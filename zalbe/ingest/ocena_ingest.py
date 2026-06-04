
import argparse
import logging
import random
import sys
import os

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from config import OCENE_COLLECTION, BATCH_SIZE
from schema.ocena_schema import ocena_schema, ocena_index_params
from services.milvus_service import milvus_service
from services.embedding_service import embedding_service

logging.basicConfig(level=logging.INFO, format="%(asctime)s  %(levelname)-8s %(message)s")
logger = logging.getLogger(__name__)


# ŠABLONI ZA GENERISANJE OCENA I KOMENTARA
# Komentari su podeljeni po oceni (negativni, neutralni, pozitivni)
# Svaka ocena se povezuje sa timom koji je rešavao žalbu
TIMOVI = ["Tim za smestaj", "Tim za prevoz", "Tim za dokumentaciju", "Tim za ostalo"]

KOMENTARI_PO_OCENI = {
    1: [  # Veoma nezadovoljan
        "Tim nije reagovao na moju žalbu, čekao sam danima bez ikakvog odgovora.",
        "Potpuno neprofesionalno, problem nije rešen a ja sam ostao bez novca.",
        "Najgore iskustvo, niko se nije potrudio da pomogne.",
        "Razočaran sam u agenciju, žalbu su ignorisali nedelju dana.",
        "Niko mi se nije javio, morao sam sam da rešavam problem.",
        "Tim je bio arogantan i nije pokazao volju za rešavanje.",
        "Izgubio sam mnogo novca i vremena, neće se ponoviti.",
        "Loša komunikacija, nikakve informacije o statusu žalbe.",
        "Problem je zataškan umesto rešen, prevaren sam.",
        "Nedopustivo sporo rešavanje, ovo nije profesionalni nivo.",
    ],
    2: [  # Nezadovoljan
        "Sporo su rešavali, iako su na kraju ponudili neku kompenzaciju.",
        "Komunikacija je bila slaba, trebalo je previše vremena.",
        "Problem je delimično rešen, ali ne u skladu sa očekivanjima.",
        "Tim je pokušao da pomogne, ali ishod nije bio zadovoljavajući.",
        "Rešenje je kasno stiglo, kada više nije bilo od koristi.",
        "Ponudili su zamenu koja nije bila ekvivalentna obećanom.",
        "Osećao sam se kao da moja žalba nije prioritet.",
        "Rešavanje je bilo haotično, bez jasne strategije.",
        "Dobio sam odgovor tek nakon više pritisaka.",
        "Previše birokratije, malo konkretnih rešenja.",
    ],
    3: [  # Neutralno
        "Problem je rešen, ali komunikacija je mogla biti bolja.",
        "Tim je obavio svoj posao, ništa posebno.",
        "Prosečno iskustvo, nema zamerki ali ni pohvala.",
        "Rešenje je bilo standardno, bez dodatnog truda.",
        "Bilo je u redu, ali sam očekivao više.",
        "Tim je profesionalan, ali bez dodatne pažnje prema putniku.",
        "Žalba je obrađena u razumnom roku, rešenje prihvatljivo.",
        "Sve je prošlo kako treba, ništa izuzetno.",
        "Solidna usluga, nema velikih primedbi.",
        "Očekivao sam malo brže rešenje, ali na kraju sve u redu.",
    ],
    4: [  # Zadovoljan
        "Tim je brzo reagovao i ponudio dobro rešenje.",
        "Zadovoljan sam kako je žalba rešena, komunikacija odlična.",
        "Osoblje je bilo ljubazno i profesionalno tokom celog procesa.",
        "Brzo rešavanje, jasna komunikacija, preporučujem.",
        "Tim je pokazao profesionalnost i razumevanje za moj problem.",
        "Odgovorili su brzo i ponudili adekvatnu kompenzaciju.",
        "Prijatno iznenađenje, rešenje bolje od očekivanog.",
        "Dobra organizacija i efikasnost, problem rešen bez komplikacija.",
        "Pohvalno, osoblje se potrudilo da pomogne.",
        "Zadovoljan kvalitetom usluge, problem rešen profesionalno.",
    ],
    5: [  # Veoma zadovoljan
        "Odlično iskustvo, tim je rešio problem brže od očekivanog!",
        "Savršena komunikacija, profesionalan pristup, svaka preporuka!",
        "Impresivno, tim je prevazišao moja očekivanja.",
        "Brzo, efikasno i profesionalno, nemam reči pohvale.",
        "Vrhunska usluga, osoblje izuzetno ljubazno i efikasno.",
        "Najbolja agencija sa kojom sam ikad radio, sve pet!",
        "Rešili su moj problem u rekordnom vremenu, neverovatno!",
        "Izuzetno zadovoljan, definitivno ću se vratiti.",
        "Profesionalizam na najvišem nivou, ogromna preporuka.",
        "Premašili su sva moja očekivanja, bravo timu!",
    ],
}


def generisi_ocene(broj_ocena: int = 250) -> list[dict]:
    """Generiše listu test ocena kombinovanjem šablona."""
    ocene = []
    # Realistična distribucija ocena: malo niskih, više visokih
    ocene_weights = [5, 10, 20, 35, 30]  # 1★, 2★, 3★, 4★, 5★

    for i in range(broj_ocena):
        ocena_broj = random.choices([1, 2, 3, 4, 5], weights=ocene_weights)[0]
        tim = random.choice(TIMOVI)
        komentar = random.choice(KOMENTARI_PO_OCENI[ocena_broj])

        ocena = {
            "ocena_id": i + 1,
            "zalba_id": random.randint(1, 250),  # povezuje se sa nekom od postojećih žalbi
            "ocena": ocena_broj,
            "tim": tim,
            "komentar": komentar,
        }
        ocene.append(ocena)

    return ocene


def pripremi_kolekciju(reset: bool = False) -> None:
    """Kreira kolekciju (opciono briše postojeću)."""
    client = milvus_service.client

    if reset and milvus_service.has_collection(OCENE_COLLECTION):
        milvus_service.drop_collection(OCENE_COLLECTION)

    if not milvus_service.has_collection(OCENE_COLLECTION):
        milvus_service.create_collection(
            collection_name=OCENE_COLLECTION,
            schema=ocena_schema(client),
            index_params=ocena_index_params(client),
        )

    milvus_service.load_collection(OCENE_COLLECTION)


def ubaci_u_bazu(ocene: list[dict]) -> None:
    """Vektorizuje komentare i ubacuje ocene u Milvus u batch-evima."""
    total = 0
    batch = []

    for ocena in ocene:
        batch.append(ocena)
        if len(batch) >= BATCH_SIZE:
            total = _flush_batch(batch, total)
            batch = []

    if batch:
        total = _flush_batch(batch, total)

    logger.info("Ukupno uneto %d ocena u kolekciju '%s'.", total, OCENE_COLLECTION)


def _flush_batch(batch: list[dict], total: int) -> int:
    """Vektorizuje komentare iz batch-a i šalje ih u Milvus."""
    komentari = [o["komentar"] for o in batch]
    vektori = embedding_service.encode(komentari)

    records = [
        {**o, "komentar_vector": vec}
        for o, vec in zip(batch, vektori)
    ]

    milvus_service.insert(OCENE_COLLECTION, records)
    total += len(records)
    logger.info("  Uneto %d slogova...", total)
    return total


def ingest(broj_ocena: int = 250, reset: bool = False) -> None:
    """Glavna funkcija - kreira kolekciju i puni je podacima."""
    pripremi_kolekciju(reset)

    postojeci_broj = milvus_service.count(OCENE_COLLECTION)
    if postojeci_broj > 0 and not reset:
        logger.info(
            "Kolekcija '%s' već ima %d slogova. Preskačem (koristi --reset za ponovno punjenje).",
            OCENE_COLLECTION, postojeci_broj
        )
        return

    logger.info("Generisanje %d ocena...", broj_ocena)
    ocene = generisi_ocene(broj_ocena)

    logger.info("Vektorizacija i unos u Milvus...")
    ubaci_u_bazu(ocene)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--broj", type=int, default=250, help="Broj ocena za generisanje")
    parser.add_argument("--reset", action="store_true", help="Briše i ponovo kreira kolekciju")
    args = parser.parse_args()
    ingest(broj_ocena=args.broj, reset=args.reset)