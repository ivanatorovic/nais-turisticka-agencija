
import os
from typing import Any

from cassandra.cluster import Cluster




CASSANDRA_HOST = os.getenv(
    "CASSANDRA_HOST",
    "cassandradb"
)

CASSANDRA_PORT = int(
    os.getenv(
        "CASSANDRA_PORT",
        "9042"
    )
)

KEYSPACE = os.getenv(
    "CASSANDRA_KEYSPACE",
    "zalbe_keyspace"
)


cluster = Cluster(
    [CASSANDRA_HOST],
    port=CASSANDRA_PORT
)

session = cluster.connect(KEYSPACE)




def row_to_dict(row) -> dict | None:
    """
    Pretvara Cassandra Row objekat u Python rečnik.
    """

    if row is None:
        return None

    return dict(row._asdict())


def rows_to_dict_list(rows) -> list[dict]:
    """
    Pretvara Cassandra ResultSet u listu Python rečnika.
    """

    return [
        dict(row._asdict())
        for row in rows
    ]


def extract_zalba_data(zalba: Any) -> dict:
    """
    Dobavlja podatke žalbe iz Pydantic modela ili rečnika.

    Ovim omogućavamo da ista funkcija radi i pri kreiranju
    nove žalbe i prilikom ažuriranja postojećeg zapisa.
    """

    if isinstance(zalba, dict):
        return zalba

    if hasattr(zalba, "model_dump"):
        return zalba.model_dump()

    raise ValueError(
        "Žalba mora biti Pydantic model ili Python rečnik."
    )




def create_zalba_cassandra(zalba) -> dict:
    """
    Upisuje žalbu u svih pet denormalizovanih Cassandra tabela.

    Cassandra tabele:
    - complaints_by_id
    - complaints_by_category
    - complaints_by_team
    - complaints_by_priority
    - complaints_by_tour
    """

    data = extract_zalba_data(zalba)

    session.execute(
        """
        INSERT INTO complaints_by_id
        (
            zalba_id,
            naslov,
            opis,
            kategorija,
            tim,
            prioritet,
            id_ture
        )
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (
            data["zalba_id"],
            data["naslov"],
            data["opis"],
            data["kategorija"],
            data["tim"],
            data["prioritet"],
            data["id_ture"],
        )
    )

    session.execute(
        """
        INSERT INTO complaints_by_category
        (
            kategorija,
            zalba_id,
            naslov,
            opis,
            tim,
            prioritet,
            id_ture
        )
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (
            data["kategorija"],
            data["zalba_id"],
            data["naslov"],
            data["opis"],
            data["tim"],
            data["prioritet"],
            data["id_ture"],
        )
    )

    session.execute(
        """
        INSERT INTO complaints_by_team
        (
            tim,
            zalba_id,
            naslov,
            opis,
            kategorija,
            prioritet,
            id_ture
        )
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (
            data["tim"],
            data["zalba_id"],
            data["naslov"],
            data["opis"],
            data["kategorija"],
            data["prioritet"],
            data["id_ture"],
        )
    )

    session.execute(
        """
        INSERT INTO complaints_by_priority
        (
            prioritet,
            zalba_id,
            naslov,
            opis,
            kategorija,
            tim,
            id_ture
        )
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (
            data["prioritet"],
            data["zalba_id"],
            data["naslov"],
            data["opis"],
            data["kategorija"],
            data["tim"],
            data["id_ture"],
        )
    )

    session.execute(
        """
        INSERT INTO complaints_by_tour
        (
            id_ture,
            zalba_id,
            naslov,
            opis,
            kategorija,
            tim,
            prioritet
        )
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """,
        (
            data["id_ture"],
            data["zalba_id"],
            data["naslov"],
            data["opis"],
            data["kategorija"],
            data["tim"],
            data["prioritet"],
        )
    )

    return data



def get_zalba_by_id_cassandra(
        zalba_id: int
) -> dict | None:
    """
    Dobavlja jednu žalbu prema njenom jedinstvenom ID-u.
    """

    row = session.execute(
        """
        SELECT *
        FROM complaints_by_id
        WHERE zalba_id = %s
        """,
        (zalba_id,)
    ).one()

    return row_to_dict(row)



def get_zalbe_by_category_cassandra(
        kategorija: str
) -> list[dict]:
    """
    Dobavlja sve žalbe iz prosleđene kategorije.
    """

    rows = session.execute(
        """
        SELECT *
        FROM complaints_by_category
        WHERE kategorija = %s
        """,
        (kategorija,)
    )

    return rows_to_dict_list(rows)



def get_zalbe_by_team_cassandra(
        tim: str
) -> list[dict]:
    """
    Dobavlja sve žalbe dodeljene prosleđenom timu.

    Ovo je osnovni Cassandra pristupni obrazac koji koristi
    prosta sekcija generatora izveštaja.
    """

    rows = session.execute(
        """
        SELECT *
        FROM complaints_by_team
        WHERE tim = %s
        """,
        (tim,)
    )

    return rows_to_dict_list(rows)



def get_zalbe_by_priority_cassandra(
        prioritet: int
) -> list[dict]:
    """
    Dobavlja sve žalbe određenog prioriteta.

    Prioritet:
    1 = normalna žalba
    2 = hitna žalba
    """

    rows = session.execute(
        """
        SELECT *
        FROM complaints_by_priority
        WHERE prioritet = %s
        """,
        (prioritet,)
    )

    return rows_to_dict_list(rows)


def get_zalbe_by_tour_cassandra(
        id_ture: int
) -> list[dict]:
    """
    Dobavlja sve žalbe koje se odnose na prosleđenu turu.
    """

    rows = session.execute(
        """
        SELECT *
        FROM complaints_by_tour
        WHERE id_ture = %s
        """,
        (id_ture,)
    )

    return rows_to_dict_list(rows)



def update_zalba_cassandra(
        zalba_id: int,
        data: dict
) -> dict | None:
    """
    Ažurira žalbu u svim denormalizovanim tabelama.

    Prvo dobavlja stari zapis, zatim ga briše iz svih tabela
    i ponovo upisuje sa izmenjenim vrednostima.
    """

    old = get_zalba_by_id_cassandra(
        zalba_id
    )

    if old is None:
        return None

    updated = {
        **old,
        **{
            key: value
            for key, value in data.items()
            if value is not None
        }
    }

    delete_zalba_cassandra(
        zalba_id
    )

    return create_zalba_cassandra(
        updated
    )



def delete_zalba_cassandra(
        zalba_id: int
) -> bool:
    """
    Briše žalbu iz svih pet denormalizovanih Cassandra tabela.
    """

    old = get_zalba_by_id_cassandra(
        zalba_id
    )

    if old is None:
        return False

    session.execute(
        """
        DELETE FROM complaints_by_id
        WHERE zalba_id = %s
        """,
        (zalba_id,)
    )

    session.execute(
        """
        DELETE FROM complaints_by_category
        WHERE kategorija = %s
        AND zalba_id = %s
        """,
        (
            old["kategorija"],
            zalba_id,
        )
    )

    session.execute(
        """
        DELETE FROM complaints_by_team
        WHERE tim = %s
        AND zalba_id = %s
        """,
        (
            old["tim"],
            zalba_id,
        )
    )

    session.execute(
        """
        DELETE FROM complaints_by_priority
        WHERE prioritet = %s
        AND zalba_id = %s
        """,
        (
            old["prioritet"],
            zalba_id,
        )
    )

    session.execute(
        """
        DELETE FROM complaints_by_tour
        WHERE id_ture = %s
        AND zalba_id = %s
        """,
        (
            old["id_ture"],
            zalba_id,
        )
    )

    return True



def count_by_category_cassandra(
        kategorija: str
) -> dict:
    """
    Vraća broj žalbi u prosleđenoj kategoriji.
    """

    rows = get_zalbe_by_category_cassandra(
        kategorija
    )

    return {
        "kategorija": kategorija,
        "broj_zalbi": len(rows),
    }


def count_by_team_cassandra(
        tim: str
) -> dict:
    """
    Vraća broj žalbi dodeljenih prosleđenom timu.
    """

    rows = get_zalbe_by_team_cassandra(
        tim
    )

    return {
        "tim": tim,
        "broj_zalbi": len(rows),
    }


def count_by_priority_cassandra(
        prioritet: int
) -> dict:
    """
    Vraća broj žalbi određenog prioriteta.
    """

    rows = get_zalbe_by_priority_cassandra(
        prioritet
    )

    return {
        "prioritet": prioritet,
        "broj_zalbi": len(rows)
    }

