from fastapi import APIRouter, HTTPException

from model.zalba_model import ZalbaCreate, ZalbaUpdate, ZalbaResponse

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

router = APIRouter(prefix="/zalbe", tags=["Analiza žalbi"])


@router.post("/", response_model=ZalbaResponse)
def create_zalba(zalba: ZalbaCreate):
    return create_zalba_cassandra(zalba)


@router.get("/{zalba_id}")
def get_zalba(zalba_id: int):
    result = get_zalba_by_id_cassandra(zalba_id)
    if result is None:
        raise HTTPException(status_code=404, detail="Žalba nije pronađena")
    return result


@router.get("/kategorija/{kategorija}")
def get_by_category(kategorija: str):
    return get_zalbe_by_category_cassandra(kategorija)


@router.get("/tim/{tim}")
def get_by_team(tim: str):
    return get_zalbe_by_team_cassandra(tim)


@router.get("/prioritet/{prioritet}")
def get_by_priority(prioritet: int):
    return get_zalbe_by_priority_cassandra(prioritet)


@router.get("/tura/{id_ture}")
def get_by_tour(id_ture: int):
    return get_zalbe_by_tour_cassandra(id_ture)


@router.put("/{zalba_id}")
def update_zalba(zalba_id: int, zalba: ZalbaUpdate):
    result = update_zalba_cassandra(zalba_id, zalba.model_dump())
    if result is None:
        raise HTTPException(status_code=404, detail="Žalba nije pronađena")
    return result


@router.delete("/{zalba_id}")
def delete_zalba(zalba_id: int):
    deleted = delete_zalba_cassandra(zalba_id)
    if not deleted:
        raise HTTPException(status_code=404, detail="Žalba nije pronađena")
    return {"message": "Žalba uspešno obrisana"}


@router.get("/count/kategorija/{kategorija}")
def count_by_category(kategorija: str):
    return count_by_category_cassandra(kategorija)


@router.get("/count/tim/{tim}")
def count_by_team(tim: str):
    return count_by_team_cassandra(tim)


@router.get("/count/prioritet/{prioritet}")
def count_by_priority(prioritet: int):
    return count_by_priority_cassandra(prioritet)