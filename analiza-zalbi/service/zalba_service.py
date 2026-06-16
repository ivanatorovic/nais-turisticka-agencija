from repository.cassandra_repository import (
    create_zalba_cassandra,
    get_zalba_by_id_cassandra,
    get_zalbe_by_category_cassandra,
    get_zalbe_by_team_cassandra,
    get_zalbe_by_priority_cassandra,
    get_zalbe_by_tour_cassandra,
    update_zalba_cassandra,
    delete_zalba_cassandra,
    count_by_category_cassandra,
    count_by_team_cassandra,
    count_by_priority_cassandra,
)

from repository.redis_repository import (
    save_zalba_to_cache,
    get_zalba_from_cache,
    delete_zalba_from_cache,
)



def create_zalba_service(zalba):
    """
    Kreira žalbu u svim denormalizovanim Cassandra tabelama,
    a zatim njen osnovni zapis čuva i u Redis kešu.
    """

    result = create_zalba_cassandra(zalba)

    save_zalba_to_cache(
        result["zalba_id"],
        result
    )

    return result




def get_zalba_by_id_service(zalba_id: int):
    """
    Prvo pokušava da dobavi žalbu iz Redis keša.

    Ako žalba nije pronađena u kešu, dobavlja je iz Cassandra
    baze i zatim rezultat upisuje u Redis.
    """

    cached = get_zalba_from_cache(zalba_id)

    if cached is not None:
        return cached

    result = get_zalba_by_id_cassandra(zalba_id)

    if result is not None:
        save_zalba_to_cache(
            zalba_id,
            result
        )

    return result



def get_zalbe_by_category_service(
        kategorija: str
):
    """
    Dobavlja sve žalbe iz prosleđene kategorije.
    Koristi Cassandra tabelu complaints_by_category.
    """

    return get_zalbe_by_category_cassandra(
        kategorija
    )


def get_zalbe_by_team_service(
        tim: str
):
    """
    Dobavlja sve žalbe dodeljene prosleđenom timu.
    Koristi Cassandra tabelu complaints_by_team.

    Ovu funkciju koristi i prosta sekcija generatora
    izveštaja, nakon čega controller dodatno filtrira
    žalbe prema minimalnom prioritetu.
    """

    return get_zalbe_by_team_cassandra(
        tim
    )


def get_zalbe_by_priority_service(
        prioritet: int
):
    """
    Dobavlja sve žalbe određenog prioriteta.
    Koristi Cassandra tabelu complaints_by_priority.
    """

    return get_zalbe_by_priority_cassandra(
        prioritet
    )


def get_zalbe_by_tour_service(
        id_ture: int
):
    """
    Dobavlja sve žalbe koje pripadaju određenoj turi.
    Koristi Cassandra tabelu complaints_by_tour.
    """

    return get_zalbe_by_tour_cassandra(
        id_ture
    )



def update_zalba_service(
        zalba_id: int,
        zalba_update
):
    """
    Ažurira žalbu u svim denormalizovanim Cassandra tabelama.

    Nakon uspešne izmene, nova verzija žalbe čuva se
    i u Redis kešu.
    """

    result = update_zalba_cassandra(
        zalba_id,
        zalba_update.model_dump()
    )

    if result is not None:
        save_zalba_to_cache(
            zalba_id,
            result
        )

    return result



def delete_zalba_service(
        zalba_id: int
):
    """
    Briše žalbu iz svih Cassandra tabela.

    Ako je brisanje uspešno, zapis se uklanja i iz
    Redis keša.
    """

    deleted = delete_zalba_cassandra(
        zalba_id
    )

    if deleted:
        delete_zalba_from_cache(
            zalba_id
        )

    return deleted



def count_by_category_service(
        kategorija: str
):
    """
    Vraća broj žalbi u prosleđenoj kategoriji.
    """

    return count_by_category_cassandra(
        kategorija
    )


def count_by_team_service(
        tim: str
):
    """
    Vraća broj žalbi dodeljenih prosleđenom timu.
    """

    return count_by_team_cassandra(
        tim
    )


def count_by_priority_service(
        prioritet: int
):
    """
    Vraća broj žalbi određenog prioriteta.
    """

    return count_by_priority_cassandra(
        prioritet
    )