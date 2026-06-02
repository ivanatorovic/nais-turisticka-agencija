CREATE KEYSPACE IF NOT EXISTS zalbe_keyspace
    WITH replication = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
        };

USE zalbe_keyspace;

CREATE TABLE IF NOT EXISTS complaints_by_id (
                                                zalba_id int PRIMARY KEY,
                                                naslov text,
                                                opis text,
                                                kategorija text,
                                                tim text,
                                                prioritet int,
                                                id_ture int
);

CREATE TABLE IF NOT EXISTS complaints_by_category (
                                                      kategorija text,
                                                      zalba_id int,
                                                      naslov text,
                                                      opis text,
                                                      tim text,
                                                      prioritet int,
                                                      id_ture int,
                                                      PRIMARY KEY (kategorija, zalba_id)
);

CREATE TABLE IF NOT EXISTS complaints_by_team (
                                                  tim text,
                                                  zalba_id int,
                                                  naslov text,
                                                  opis text,
                                                  kategorija text,
                                                  prioritet int,
                                                  id_ture int,
                                                  PRIMARY KEY (tim, zalba_id)
);

CREATE TABLE IF NOT EXISTS complaints_by_priority (
                                                      prioritet int,
                                                      zalba_id int,
                                                      naslov text,
                                                      opis text,
                                                      kategorija text,
                                                      tim text,
                                                      id_ture int,
                                                      PRIMARY KEY (prioritet, zalba_id)
);

CREATE TABLE IF NOT EXISTS complaints_by_tour (
                                                  id_ture int,
                                                  zalba_id int,
                                                  naslov text,
                                                  opis text,
                                                  kategorija text,
                                                  tim text,
                                                  prioritet int,
                                                  PRIMARY KEY (id_ture, zalba_id)
);
