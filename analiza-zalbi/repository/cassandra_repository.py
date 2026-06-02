import os
from cassandra.cluster import Cluster

CASSANDRA_HOST = os.getenv("CASSANDRA_HOST", "cassandradb")
CASSANDRA_PORT = int(os.getenv("CASSANDRA_PORT", "9042"))
KEYSPACE = os.getenv("CASSANDRA_KEYSPACE", "zalbe_keyspace")

cluster = Cluster([CASSANDRA_HOST], port=CASSANDRA_PORT)
session = cluster.connect(KEYSPACE)


def create_zalba_cassandra(zalba):
    data = zalba.model_dump()

    session.execute("""
        INSERT INTO complaints_by_id
        (zalba_id, naslov, opis, kategorija, tim, prioritet, id_ture)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
    """, (data["zalba_id"], data["naslov"], data["opis"], data["kategorija"], data["tim"], data["prioritet"], data["id_ture"]))

    session.execute("""
        INSERT INTO complaints_by_category
        (kategorija, zalba_id, naslov, opis, tim, prioritet, id_ture)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
    """, (data["kategorija"], data["zalba_id"], data["naslov"], data["opis"], data["tim"], data["prioritet"], data["id_ture"]))

    session.execute("""
        INSERT INTO complaints_by_team
        (tim, zalba_id, naslov, opis, kategorija, prioritet, id_ture)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
    """, (data["tim"], data["zalba_id"], data["naslov"], data["opis"], data["kategorija"], data["prioritet"], data["id_ture"]))

    session.execute("""
        INSERT INTO complaints_by_priority
        (prioritet, zalba_id, naslov, opis, kategorija, tim, id_ture)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
    """, (data["prioritet"], data["zalba_id"], data["naslov"], data["opis"], data["kategorija"], data["tim"], data["id_ture"]))

    session.execute("""
        INSERT INTO complaints_by_tour
        (id_ture, zalba_id, naslov, opis, kategorija, tim, prioritet)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
    """, (data["id_ture"], data["zalba_id"], data["naslov"], data["opis"], data["kategorija"], data["tim"], data["prioritet"]))

    return data


def get_zalba_by_id_cassandra(zalba_id: int):
    row = session.execute(
        "SELECT * FROM complaints_by_id WHERE zalba_id = %s",
        (zalba_id,)
    ).one()

    if row is None:
        return None

    return dict(row._asdict())


def get_zalbe_by_category_cassandra(kategorija: str):
    rows = session.execute(
        "SELECT * FROM complaints_by_category WHERE kategorija = %s",
        (kategorija,)
    )
    return [dict(row._asdict()) for row in rows]


def get_zalbe_by_team_cassandra(tim: str):
    rows = session.execute(
        "SELECT * FROM complaints_by_team WHERE tim = %s",
        (tim,)
    )
    return [dict(row._asdict()) for row in rows]


def get_zalbe_by_priority_cassandra(prioritet: int):
    rows = session.execute(
        "SELECT * FROM complaints_by_priority WHERE prioritet = %s",
        (prioritet,)
    )
    return [dict(row._asdict()) for row in rows]


def get_zalbe_by_tour_cassandra(id_ture: int):
    rows = session.execute(
        "SELECT * FROM complaints_by_tour WHERE id_ture = %s",
        (id_ture,)
    )
    return [dict(row._asdict()) for row in rows]


def update_zalba_cassandra(zalba_id: int, data: dict):
    old = get_zalba_by_id_cassandra(zalba_id)
    if old is None:
        return None

    updated = {**old, **{k: v for k, v in data.items() if v is not None}}
    delete_zalba_cassandra(zalba_id)

    class Obj:
        def model_dump(self):
            return updated

    return create_zalba_cassandra(Obj())


def delete_zalba_cassandra(zalba_id: int):
    old = get_zalba_by_id_cassandra(zalba_id)
    if old is None:
        return False

    session.execute("DELETE FROM complaints_by_id WHERE zalba_id = %s", (zalba_id,))
    session.execute("DELETE FROM complaints_by_category WHERE kategorija = %s AND zalba_id = %s", (old["kategorija"], zalba_id))
    session.execute("DELETE FROM complaints_by_team WHERE tim = %s AND zalba_id = %s", (old["tim"], zalba_id))
    session.execute("DELETE FROM complaints_by_priority WHERE prioritet = %s AND zalba_id = %s", (old["prioritet"], zalba_id))
    session.execute("DELETE FROM complaints_by_tour WHERE id_ture = %s AND zalba_id = %s", (old["id_ture"], zalba_id))

    return True


def count_by_category_cassandra(kategorija: str):
    rows = get_zalbe_by_category_cassandra(kategorija)
    return {"kategorija": kategorija, "broj_zalbi": len(rows)}


def count_by_team_cassandra(tim: str):
    rows = get_zalbe_by_team_cassandra(tim)
    return {"tim": tim, "broj_zalbi": len(rows)}


def count_by_priority_cassandra(prioritet: int):
    rows = get_zalbe_by_priority_cassandra(prioritet)
    return {"prioritet": prioritet, "broj_zalbi": len(rows)}