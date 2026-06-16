
import os
from collections import Counter
from urllib.parse import quote

import requests


MILVUS_SERVICE_URL = os.getenv(
    "MILVUS_SERVICE_URL",
    "http://zalbe-service:8000"
)

CASSANDRA_SERVICE_URL = os.getenv(
    "CASSANDRA_SERVICE_URL",
    "http://analiza-zalbi:8000"
)

REQUEST_TIMEOUT = 20


def dobavi_cassandra_prostu_sekciju(
        tim: str,
        minimalni_prioritet: int,
        limit: int
) -> list[dict]:
    """
    Prosta sekcija iz Cassandra baze.

    Dobavlja žalbe određenog tima koje imaju prioritet
    veći ili jednak prosleđenoj vrednosti.
    """

    kodirani_tim = quote(
        tim,
        safe=""
    )

    url = (
        f"{CASSANDRA_SERVICE_URL}"
        f"/zalbe/izvestaj/tim/{kodirani_tim}"
    )

    response = requests.get(
        url,
        params={
            "minimalni_prioritet": minimalni_prioritet,
            "limit": limit,
        },
        timeout=REQUEST_TIMEOUT
    )

    response.raise_for_status()

    return response.json()


def dobavi_milvus_prostu_sekciju(
        kategorija: str,
        limit: int
) -> list[dict]:
    """
    Prosta sekcija iz Milvus baze.

    Dobavlja žalbe iz prosleđene kategorije.
    """

    kodirana_kategorija = quote(
        kategorija,
        safe=""
    )

    url = (
        f"{MILVUS_SERVICE_URL}"
        f"/zalbe/filter/kategorija/{kodirana_kategorija}"
    )

    response = requests.get(
        url,
        params={
            "limit": limit,
        },
        timeout=REQUEST_TIMEOUT
    )

    response.raise_for_status()

    return response.json()


def dobavi_milvus_slozenu_sekciju(
        tekst_upita: str,
        kategorija: str,
        prioritet: int,
        minimalna_slicnost: float,
        top_k: int
) -> list[dict]:
    """
    Složena sekcija iz Milvus baze.

    Izvršava vektorsku pretragu uz:
    - semantičko poređenje opisa;
    - filter kategorije;
    - filter prioriteta;
    - minimalni prag sličnosti.
    """

    url = (
        f"{MILVUS_SERVICE_URL}"
        "/zalbe/search/filter"
    )

    response = requests.post(
        url,
        json={
            "tekst_upita": tekst_upita,
            "kategorija": kategorija,
            "prioritet": prioritet,
            "top_k": top_k,
        },
        timeout=REQUEST_TIMEOUT
    )

    response.raise_for_status()

    rezultati = response.json()

    filtrirani_rezultati = []

    for rezultat in rezultati:
        slicnost = float(
            rezultat.get(
                "distance",
                0
            )
        )

        if slicnost >= minimalna_slicnost:
            filtrirani_rezultati.append({
                **rezultat,
                "slicnost": round(
                    slicnost,
                    4
                ),
                "slicnost_procenat": round(
                    slicnost * 100,
                    2
                ),
            })

    filtrirani_rezultati.sort(
        key=lambda zapis: zapis["slicnost"],
        reverse=True
    )

    return filtrirani_rezultati


def dobavi_podatke_za_grafikon() -> list[dict]:
    """
    Dobavlja sve žalbe iz Milvusa i grupiše ih
    prema kategoriji.

    Rezultat je prilagođen Grafana bar chart panelu.
    """

    url = (
        f"{MILVUS_SERVICE_URL}"
        "/zalbe/"
    )

    response = requests.get(
        url,
        params={
            "limit": 1000,
        },
        timeout=REQUEST_TIMEOUT
    )

    response.raise_for_status()

    zalbe = response.json()

    broj_po_kategoriji = Counter(
        zalba.get(
            "kategorija",
            "nepoznato"
        )
        for zalba in zalbe
    )

    rezultat = [
        {
            "kategorija": kategorija,
            "broj_zalbi": broj,
        }
        for kategorija, broj
        in broj_po_kategoriji.items()
    ]

    rezultat.sort(
        key=lambda zapis: zapis["broj_zalbi"],
        reverse=True
    )

    return rezultat


def formiraj_pregled_izvestaja(
        tim: str,
        minimalni_prioritet: int,
        kategorija: str,
        tekst_upita: str,
        prioritet: int,
        minimalna_slicnost: float,
        limit: int,
        top_k: int
) -> dict:
    """
    Formira jednu smislenu celinu izveštaja
    koristeći podatke iz Cassandra i Milvus baze.
    """

    cassandra_sekcija = (
        dobavi_cassandra_prostu_sekciju(
            tim=tim,
            minimalni_prioritet=minimalni_prioritet,
            limit=limit
        )
    )

    milvus_prosta_sekcija = (
        dobavi_milvus_prostu_sekciju(
            kategorija=kategorija,
            limit=limit
        )
    )

    milvus_slozena_sekcija = (
        dobavi_milvus_slozenu_sekciju(
            tekst_upita=tekst_upita,
            kategorija=kategorija,
            prioritet=prioritet,
            minimalna_slicnost=minimalna_slicnost,
            top_k=top_k
        )
    )

    grafikon = dobavi_podatke_za_grafikon()

    return {
        "naziv_izvestaja": (
            "Analiza žalbi putnika turističke agencije"
        ),
        "opis": (
            "Izveštaj objedinjuje podatke iz Cassandra "
            "i Milvus NoSQL baze."
        ),
        "parametri": {
            "tim": tim,
            "minimalni_prioritet": minimalni_prioritet,
            "kategorija": kategorija,
            "tekst_upita": tekst_upita,
            "prioritet": prioritet,
            "minimalna_slicnost": minimalna_slicnost,
            "limit": limit,
            "top_k": top_k,
        },
        "prosta_sekcija_cassandra": {
            "naslov": (
                "Žalbe određenog tima sa minimalnim prioritetom"
            ),
            "broj_rezultata": len(
                cassandra_sekcija
            ),
            "podaci": cassandra_sekcija,
        },
        "prosta_sekcija_milvus": {
            "naslov": (
                "Žalbe iz izabrane kategorije"
            ),
            "broj_rezultata": len(
                milvus_prosta_sekcija
            ),
            "podaci": milvus_prosta_sekcija,
        },
        "slozena_sekcija_milvus": {
            "naslov": (
                "Semantički slične žalbe uz filtere"
            ),
            "minimalna_slicnost_procenat": (
                    minimalna_slicnost * 100
            ),
            "broj_rezultata": len(
                milvus_slozena_sekcija
            ),
            "podaci": milvus_slozena_sekcija,
        },
        "grafikon_broj_zalbi_po_kategoriji": {
            "naslov": (
                "Broj žalbi po kategoriji"
            ),
            "podaci": grafikon
        }
    }

