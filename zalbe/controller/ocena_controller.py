from fastapi import APIRouter, HTTPException
from typing import List

from model.ocena_model import OcenaCreate, OcenaUpdate, OcenaResponse, CountResponse
from repository.ocena_repository import (
    create_ocena, get_ocena_by_id, get_all_ocene,
    update_ocena, delete_ocena,
    prebroj_ocene_po_uslovu,
)

router = APIRouter(prefix="/ocene", tags=["Ocene resavanja"])




@router.post("/", response_model=dict, summary="Kreiraj novu ocenu")
def kreiraj_ocenu(ocena: OcenaCreate):
    """Dodaje novu ocenu u bazu."""
    return create_ocena(ocena.model_dump())


@router.get("/{ocena_id}", response_model=OcenaResponse, summary="Dohvati ocenu po ID-u")
def dohvati_ocenu(ocena_id: int):
    """Vraća ocenu sa zadatim ID-em."""
    ocena = get_ocena_by_id(ocena_id)
    if not ocena:
        raise HTTPException(status_code=404, detail=f"Ocena ID={ocena_id} ne postoji")
    return ocena


@router.get("/", response_model=List[OcenaResponse], summary="Dohvati sve ocene")
def dohvati_sve_ocene(limit: int = 100):
    """Vraća sve ocene (sa limitom)."""
    return get_all_ocene(limit=limit)


@router.put("/{ocena_id}", response_model=dict, summary="Ažuriraj ocenu")
def azuriraj_ocenu(ocena_id: int, izmene: OcenaUpdate):
    """Ažurira postojeću ocenu."""
    nova_polja = izmene.model_dump(exclude_unset=True)
    if not nova_polja:
        raise HTTPException(status_code=400, detail="Nema polja za ažuriranje")
    return update_ocena(ocena_id, nova_polja)


@router.delete("/{ocena_id}", response_model=dict, summary="Obriši ocenu")
def obrisi_ocenu(ocena_id: int):
    """Briše ocenu po ID-u."""
    return delete_ocena(ocena_id)




@router.get("/count/uslov", response_model=CountResponse,
            summary="Prebroj ocene po uslovu")
def prebroj_po_uslovu(filter_expr: str):
    broj = prebroj_ocene_po_uslovu(filter_expr)
    return {"filter_expr": filter_expr, "count": broj}