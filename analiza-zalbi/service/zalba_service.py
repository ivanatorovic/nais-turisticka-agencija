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
    result = create_zalba_cassandra(zalba)
    save_zalba_to_cache(result["zalba_id"], result)
    return result


def get_zalba_by_id_service(zalba_id: int):
    cached = get_zalba_from_cache(zalba_id)
    if cached is not None:
        return cached

    result = get_zalba_by_id_cassandra(zalba_id)
    if result is not None:
        save_zalba_to_cache(zalba_id, result)

    return result


def get_zalbe_by_category_service(kategorija: str):
    return get_zalbe_by_category_cassandra(kategorija)


def get_zalbe_by_team_service(tim: str):
    return get_zalbe_by_team_cassandra(tim)


def get_zalbe_by_priority_service(prioritet: int):
    return get_zalbe_by_priority_cassandra(prioritet)


def get_zalbe_by_tour_service(id_ture: int):
    return get_zalbe_by_tour_cassandra(id_ture)


def update_zalba_service(zalba_id: int, zalba_update):
    result = update_zalba_cassandra(zalba_id, zalba_update.model_dump())
    if result is not None:
        save_zalba_to_cache(zalba_id, result)
    return result


def delete_zalba_service(zalba_id: int):
    deleted = delete_zalba_cassandra(zalba_id)
    if deleted:
        delete_zalba_from_cache(zalba_id)
    return deleted


def count_by_category_service(kategorija: str):
    return count_by_category_cassandra(kategorija)


def count_by_team_service(tim: str):
    return count_by_team_cassandra(tim)


def count_by_priority_service(prioritet: int):
    return count_by_priority_cassandra(prioritet)