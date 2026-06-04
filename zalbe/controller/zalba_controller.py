from fastapi import APIRouter, HTTPException
from typing import List

from model.zalba_model import (
    ZalbaCreate, ZalbaUpdate, ZalbaResponse,
    ZalbaSearchRequest, ZalbaIteratorRequest, ZalbaHybridRequest,
    ZalbaSearchResult,
)

from repository.zalba_repository import (
    create_zalba, get_zalba_by_id, get_all_zalbe, get_zalbe_by_filter,
    count_zalbe, update_zalba, delete_zalba, delete_zalbe_by_filter,
    filtriraj_zalbe_po_kategoriji,
    pretrazi_slicne_zalbe_po_kategoriji_i_prioritetu,
    pretrazi_slicne_zalbe_po_timu_iterator,
    hibridna_pretraga_po_naslovu_i_opisu,
)

router = APIRouter(prefix="/zalbe", tags=["Zalbe"])


@router.post("/", response_model=dict, summary="Kreiraj novu žalbu")
def kreiraj_zalbu(zalba: ZalbaCreate):
    return create_zalba(zalba.model_dump())


@router.get("/{zalba_id}", response_model=ZalbaResponse, summary="Dohvati žalbu po ID-u")
def dohvati_zalbu(zalba_id: int):
    zalba = get_zalba_by_id(zalba_id)
    if not zalba:
        raise HTTPException(status_code=404, detail=f"Žalba ID={zalba_id} ne postoji")
    return zalba


@router.get("/", response_model=List[ZalbaResponse], summary="Dohvati sve žalbe")
def dohvati_sve_zalbe(limit: int = 100):
    return get_all_zalbe(limit=limit)


@router.put("/{zalba_id}", response_model=dict, summary="Ažuriraj žalbu")
def azuriraj_zalbu(zalba_id: int, izmene: ZalbaUpdate):
    nova_polja = izmene.model_dump(exclude_unset=True)
    if not nova_polja:
        raise HTTPException(status_code=400, detail="Nema polja za ažuriranje")
    return update_zalba(zalba_id, nova_polja)


@router.delete("/{zalba_id}", response_model=dict, summary="Obriši žalbu")
def obrisi_zalbu(zalba_id: int):
    return delete_zalba(zalba_id)


@router.get("/filter/kategorija/{kategorija}", response_model=List[dict],
            summary="Filtriraj žalbe po kategoriji")
def filter_po_kategoriji(kategorija: str, limit: int = 10):
    return filtriraj_zalbe_po_kategoriji(kategorija, limit=limit)


@router.get("/count/all", summary="Prebroj sve žalbe")
def prebroj_sve_zalbe():
    return {"count": count_zalbe()}


@router.post("/search/filter", response_model=List[ZalbaSearchResult],
             summary="Vektorska pretraga + filter")
def slozen_upit_filter(zahtev: ZalbaSearchRequest):
    return pretrazi_slicne_zalbe_po_kategoriji_i_prioritetu(
        tekst_upita=zahtev.tekst_upita,
        kategorija=zahtev.kategorija,
        prioritet=zahtev.prioritet,
        top_k=zahtev.top_k
    )


@router.post("/search/iterator", response_model=List[ZalbaSearchResult],
             summary="Vektorska pretraga + iterator")
def slozen_upit_iterator(zahtev: ZalbaIteratorRequest):
    return pretrazi_slicne_zalbe_po_timu_iterator(
        tekst_upita=zahtev.tekst_upita,
        tim=zahtev.tim,
        batch_size=zahtev.batch_size
    )


@router.post("/search/hybrid", response_model=List[ZalbaSearchResult],
             summary="Hibridna pretraga")
def slozen_upit_hibridna(zahtev: ZalbaHybridRequest):
    return hibridna_pretraga_po_naslovu_i_opisu(
        tekst_upita=zahtev.tekst_upita,
        top_k=zahtev.top_k
    )