
import argparse
import logging
import random
import sys
import os

# Dodajemo root folder u path (da možemo da importujemo config, schema, services)
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from config import ZALBE_COLLECTION, BATCH_SIZE
from schema.zalba_schema import zalba_schema, zalba_index_params
from services.milvus_service import milvus_service
from services.embedding_service import embedding_service

logging.basicConfig(level=logging.INFO, format="%(asctime)s  %(levelname)-8s %(message)s")
logger = logging.getLogger(__name__)

# ============================================================
# ŠABLONI ZA GENERISANJE ŽALBI
# Svaka kategorija ima tim koji je rešava i listu tipičnih problema
# ============================================================

ZALBE_SABLONI = {
    "smestaj": {
        "tim": "Tim za smestaj",
        "naslovi": [
            "Problem sa hotelskom sobom",
            "Loši uslovi smeštaja",
            "Klima ne radi u sobi",
            "Prljava soba",
            "Hotel nije kao što je reklamirano",
            "Bučna soba, ne može se spavati",
            "Topla voda ne radi",
            "Wi-Fi ne radi u sobi",
            "Soba premale površine",
            "Plesan u kupatilu",
        ],
        "opisi": [
            "Soba u hotelu je bila prljava, posteljina nije promenjena, klima uredjaj nije radio. Recepcija nije reagovala na žalbu.",
            "Hotel je reklamiran kao četvorozvezdani, a u stvarnosti je bio u lošem stanju. Sobe stare, nameštaj oštećen.",
            "Klima uredjaj u sobi nije radio tokom celog boravka. Temperatura preko 30 stepeni, nemoguće spavati.",
            "Topla voda nije bila dostupna tri dana. Recepcija nije uspela da reši problem.",
            "Soba je bila mnogo bučna zbog blizine lifta i recepcije. Noću se ništa nije moglo čuti.",
            "Wi-Fi internet nije funkcionisao u sobi, iako je u opisu hotela pisalo da je dostupan.",
            "Plesan u kupatilu, loše održavanje, osećaj neprijatnog mirisa u celom apartmanu.",
            "Soba je bila manja nego što je prikazano na slikama prilikom rezervacije.",
            "Hotelski restoran je bio zatvoren tokom celog boravka, iako je u aranžmanu uključen doručak.",
            "Bazen je bio van funkcije, iako je to bila jedna od glavnih atrakcija hotela.",
        ]
    },
    "prevoz": {
        "tim": "Tim za prevoz",
        "naslovi": [
            "Kašnjenje aviona",
            "Otkazan let",
            "Izgubljen prtljag",
            "Autobus u lošem stanju",
            "Transfer nije stigao",
            "Zamena tipa prevoza",
            "Oštećen prtljag",
            "Problem sa sedištem u avionu",
            "Kašnjenje autobusa",
            "Nepristojno osoblje prevoznika",
        ],
        "opisi": [
            "Avion je kasnio više od 4 sata bez ikakvog obaveštenja. Putnici su čekali na aerodromu bez informacija.",
            "Let je otkazan dan pre polaska. Ponudjena je zamena sa veoma lošim vremenima i dužim trajanjem.",
            "Prtljag je izgubljen tokom transfera. Aviokompanija nije uspela da ga locira nakon nedelju dana.",
            "Autobus za transfer je bio star i u lošem stanju. Klima nije radila, sedišta oštećena.",
            "Transfer sa aerodroma nije stigao. Čekali smo preko dva sata, na kraju smo morali da uzmemo taksi.",
            "Umesto obećanog avionskog prevoza, dobili smo zamenu autobusom koji je trajao 15 sati duže.",
            "Prtljag je stigao oštećen, sa polomljenom ručkom i ogrebanom površinom.",
            "Sedište u avionu je bilo polomljeno, nije se moglo nasloniti tokom celog leta.",
            "Autobus je kasnio 3 sata, čime je propušten prvi dan programa.",
            "Osoblje prevoznika bilo je nepristojno i nije pružilo nikakvu pomoć oko problema.",
        ]
    },
    "dokumentacija": {
        "tim": "Tim za dokumentaciju",
        "naslovi": [
            "Pogrešno ime u rezervaciji",
            "Nedostaje viza",
            "Pogrešan datum u dokumentima",
            "Greška u pasošu",
            "Nedostaje vaučer",
            "Pogrešno napisano prezime",
            "Greška u broju pasoša",
            "Nedostaje potvrda rezervacije",
            "Pogrešna destinacija na vaučeru",
            "Problem sa nazivom hotela u dokumentima",
        ],
        "opisi": [
            "U rezervaciji je pogrešno napisano moje ime, što je izazvalo problem pri prijavi na letu.",
            "Nije mi izdata viza iako je bila deo aranžmana. Morao sam da je vadim sam u poslednjem trenutku.",
            "Datum povratka u dokumentaciji je pogrešan, razlikuje se od ugovorenog aranžmana.",
            "Greška u broju pasoša na vaučeru, zbog čega nisam mogao da se prijavim u hotel.",
            "Vaučer za hotel nije bio priložen uz ostalu dokumentaciju. Bez njega nismo mogli da se prijavimo.",
            "Prezime je napisano sa pravopisnom greškom, što je dovelo do problema na graničnom prelazu.",
            "U dokumentaciji je bila pogrešna destinacija, što je otkriveno tek na aerodromu.",
            "Naziv hotela u vaučeru se razlikuje od onog koji sam rezervisao i platio.",
            "Potvrda rezervacije nije stigla mejlom, morao sam da je ganjam telefonom danima.",
            "Problem sa dokumentacijom za dete, nedostaju podaci pratilaca.",
        ]
    },
    "ostalo": {
        "tim": "Tim za ostalo",
        "naslovi": [
            "Neprofesionalan vodič",
            "Izmena programa bez najave",
            "Loša organizacija izleta",
            "Nedostupna podrška agencije",
            "Problem sa plaćanjem",
            "Izlet otkazan bez obaveštenja",
            "Vodič ne govori jezik putnika",
            "Kasnjenje sa polaskom izleta",
            "Nedostatak informacija tokom putovanja",
            "Problem sa lokalnim predstavnikom",
        ],
        "opisi": [
            "Vodič na izletu je bio nepripremljen, nije poznavao destinaciju i nije mogao da odgovori na pitanja.",
            "Program putovanja je izmenjen bez prethodnog obaveštenja putnika. Izostavljena su dva važna izleta.",
            "Izlet je bio loše organizovan, nedovoljno vremena na lokacijama, previše vremena u prevozu.",
            "Pokušavao sam da kontaktiram agenciju tokom putovanja, niko se nije javljao na telefon.",
            "Problem sa plaćanjem preko kartice, novac je skinut dva puta a rezervacija nije potvrđena.",
            "Izlet koji smo platili unapred je otkazan bez obaveštenja i bez povrata novca.",
            "Lokalni vodič nije govorio srpski ni engleski jezik, što je onemogućilo normalnu komunikaciju.",
            "Polazak izleta je kasnio preko sat vremena bez ikakvog objašnjenja.",
            "Tokom putovanja nismo dobijali informacije o narednim aktivnostima, sve je bilo haotično.",
            "Lokalni predstavnik agencije nije bio dostupan, iako je to garantovano u ugovoru.",
        ]
    }
}


def generisi_zalbe(broj_zalbi: int = 250) -> list[dict]:
    """Generiše listu test žalbi kombinovanjem šablona."""
    zalbe = []
    kategorije = list(ZALBE_SABLONI.keys())

    for i in range(broj_zalbi):
        kategorija = random.choice(kategorije)
        sablon = ZALBE_SABLONI[kategorija]

        zalba = {
            "zalba_id": i + 1,
            "naslov": random.choice(sablon["naslovi"]),
            "opis": random.choice(sablon["opisi"]),
            "kategorija": kategorija,
            "tim": sablon["tim"],
            "prioritet": random.choices([1, 2], weights=[80, 20])[0],  # 20% hitne
            "id_ture": random.randint(1000, 9999),
        }
        zalbe.append(zalba)

    return zalbe


def pripremi_kolekciju(reset: bool = False) -> None:
    """Kreira kolekciju (opciono briše postojeću)."""
    client = milvus_service.client

    if reset and milvus_service.has_collection(ZALBE_COLLECTION):
        milvus_service.drop_collection(ZALBE_COLLECTION)

    if not milvus_service.has_collection(ZALBE_COLLECTION):
        milvus_service.create_collection(
            collection_name=ZALBE_COLLECTION,
            schema=zalba_schema(client),
            index_params=zalba_index_params(client),
        )

    milvus_service.load_collection(ZALBE_COLLECTION)


def ubaci_u_bazu(zalbe: list[dict]) -> None:
    """Vektorizuje opise i ubacuje žalbe u Milvus u batch-evima."""
    total = 0
    batch = []

    for zalba in zalbe:
        batch.append(zalba)
        if len(batch) >= BATCH_SIZE:
            total = _flush_batch(batch, total)
            batch = []

    # Ubaci preostale (ako nije deljivo sa BATCH_SIZE)
    if batch:
        total = _flush_batch(batch, total)

    logger.info("Ukupno uneto %d zalbi u kolekciju '%s'.", total, ZALBE_COLLECTION)


def _flush_batch(batch: list[dict], total: int) -> int:
    """Vektorizuje naslove i opise iz batch-a i šalje ih u Milvus."""
    naslovi = [z["naslov"] for z in batch]
    opisi = [z["opis"] for z in batch]
    
    naslov_vektori = embedding_service.encode(naslovi)
    opis_vektori = embedding_service.encode(opisi)

    records = [
        {**z, "naslov_vector": nv, "opis_vector": ov}
        for z, nv, ov in zip(batch, naslov_vektori, opis_vektori)
    ]

    milvus_service.insert(ZALBE_COLLECTION, records)
    total += len(records)
    logger.info("  Uneto %d slogova...", total)
    return total


def ingest(broj_zalbi: int = 250, reset: bool = False) -> None:
    """Glavna funkcija - kreira kolekciju i puni je podacima."""
    pripremi_kolekciju(reset)

    # Proveri da li već ima podataka
    postojeci_broj = milvus_service.count(ZALBE_COLLECTION)
    if postojeci_broj > 0 and not reset:
        logger.info(
            "Kolekcija '%s' već ima %d slogova. Preskačem (koristi --reset za ponovno punjenje).",
            ZALBE_COLLECTION, postojeci_broj
        )
        return

    logger.info("Generisanje %d žalbi...", broj_zalbi)
    zalbe = generisi_zalbe(broj_zalbi)

    logger.info("Vektorizacija i unos u Milvus...")
    ubaci_u_bazu(zalbe)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--broj", type=int, default=250, help="Broj žalbi za generisanje")
    parser.add_argument("--reset", action="store_true", help="Briše i ponovo kreira kolekciju")
    args = parser.parse_args()
    ingest(broj_zalbi=args.broj, reset=args.reset)