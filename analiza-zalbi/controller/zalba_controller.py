from fastapi import APIRouter, HTTPException

from model.zalba_model import ZalbaCreate, ZalbaUpdate, ZalbaResponse

from service.zalba_service import (
    create_zalba_service,
    get_zalba_by_id_service,
    get_zalbe_by_category_service,
    get_zalbe_by_team_service,
    get_zalbe_by_priority_service,
    get_zalbe_by_tour_service,
    update_zalba_service,
    delete_zalba_service,
    count_by_category_service,
    count_by_team_service,
    count_by_priority_service,
)

router = APIRouter(prefix="/zalbe", tags=["Analiza žalbi"])


@router.post("/", response_model=ZalbaResponse)
def create_zalba(zalba: ZalbaCreate):
    return create_zalba_service(zalba)


@router.get("/{zalba_id}")
def get_zalba(zalba_id: int):
    result = get_zalba_by_id_service(zalba_id)
    if result is None:
        raise HTTPException(status_code=404, detail="Žalba nije pronađena")
    return result


@router.get("/kategorija/{kategorija}")
def get_by_category(kategorija: str):
    return get_zalbe_by_category_service(kategorija)


@router.get("/tim/{tim}")
def get_by_team(tim: str):
    return get_zalbe_by_team_service(tim)


@router.get("/prioritet/{prioritet}")
def get_by_priority(prioritet: int):
    return get_zalbe_by_priority_service(prioritet)


@router.get("/tura/{id_ture}")
def get_by_tour(id_ture: int):
    return get_zalbe_by_tour_service(id_ture)


@router.put("/{zalba_id}")
def update_zalba(zalba_id: int, zalba: ZalbaUpdate):
    result = update_zalba_service(zalba_id, zalba)
    if result is None:
        raise HTTPException(status_code=404, detail="Žalba nije pronađena")
    return result


@router.delete("/{zalba_id}")
def delete_zalba(zalba_id: int):
    deleted = delete_zalba_service(zalba_id)
    if not deleted:
        raise HTTPException(status_code=404, detail="Žalba nije pronađena")
    return {"message": "Žalba uspešno obrisana"}


@router.get("/count/kategorija/{kategorija}")
def count_by_category(kategorija: str):
    return count_by_category_service(kategorija)


@router.get("/count/tim/{tim}")
def count_by_team(tim: str):
    return count_by_team_service(tim)


@router.get("/count/prioritet/{prioritet}")
def count_by_priority(prioritet: int):
    return count_by_priority_service(prioritet)