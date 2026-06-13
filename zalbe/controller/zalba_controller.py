from typing import List
from uuid import uuid4

from fastapi import APIRouter, HTTPException

from messaging.rabbitmq_publisher import publish_complaint_created

from model.zalba_model import (
    ZalbaCreate,
    ZalbaZaAktivnostCreate,
    ZalbaUpdate,
    ZalbaResponse,
    ZalbaSearchRequest,
    ZalbaIteratorRequest,
    ZalbaHybridRequest,
    ZalbaSearchResult,
)

from repository.zalba_repository import (
    create_zalba,
    get_zalba_by_id,
    get_all_zalbe,
    count_zalbe,
    update_zalba,
    delete_zalba,
    filtriraj_zalbe_po_kategoriji,
    pretrazi_slicne_zalbe_po_kategoriji_i_prioritetu,
    pretrazi_slicne_zalbe_po_timu_iterator,
    hibridna_pretraga_po_naslovu_i_opisu,
)


router = APIRouter(
    prefix="/zalbe",
    tags=["Zalbe"]
)


@router.post(
    "/",
    response_model=dict,
    summary="Kreiraj novu žalbu"
)
def kreiraj_zalbu(zalba: ZalbaCreate):
    if get_zalba_by_id(zalba.zalba_id):
        raise HTTPException(
            status_code=409,
            detail=f"Žalba ID={zalba.zalba_id} već postoji"
        )

    record = {
        **zalba.model_dump(),
        "aktivnost_id": 0,

        "saga_id": "",
        "saga_status": "NOT_APPLICABLE"
    }

    return create_zalba(record)


@router.post(
    "/aktivnost",
    response_model=dict,
    summary="Podnesi žalbu na dodatnu aktivnost"
)
def kreiraj_zalbu_na_aktivnost(
        zalba: ZalbaZaAktivnostCreate
):
    if get_zalba_by_id(zalba.zalba_id):
        raise HTTPException(
            status_code=409,
            detail=f"Žalba ID={zalba.zalba_id} već postoji"
        )

    saga_id = str(uuid4())

    record = {
        "zalba_id": zalba.zalba_id,
        "naslov": zalba.naslov,
        "opis": zalba.opis,
        "kategorija": "dodatna_aktivnost",
        "tim": "Tim za dodatne aktivnosti",
        "prioritet": zalba.prioritet,
        "id_ture": zalba.id_ture,
        "aktivnost_id": zalba.aktivnost_id,
        "putnik_id": zalba.putnik_id,
        "saga_id": saga_id,
        "saga_status": "PENDING"
    }

    try:
        create_zalba(record)

        publish_complaint_created(
            saga_id=saga_id,
            zalba_id=zalba.zalba_id,
            aktivnost_id=zalba.aktivnost_id,
            putnik_id=zalba.putnik_id
        )

        return {
            "status": "PENDING",
            "message": (
                "Žalba je sačuvana i prosleđena "
                "podsistemu dodatnih aktivnosti."
            ),
            "saga_id": saga_id,
            "zalba_id": zalba.zalba_id,
            "aktivnost_id": zalba.aktivnost_id,
            "putnik_id": zalba.putnik_id
        }

    except Exception as exception:
        try:
            if get_zalba_by_id(zalba.zalba_id):
                delete_zalba(zalba.zalba_id)
        except Exception:
            pass

        raise HTTPException(
            status_code=503,
            detail=(
                "Nije moguće pokrenuti Saga transakciju: "
                f"{str(exception)}"
            )
        ) from exception


@router.get(
    "/filter/kategorija/{kategorija}",
    response_model=List[dict],
    summary="Filtriraj žalbe po kategoriji"
)
def filter_po_kategoriji(
        kategorija: str,
        limit: int = 10
):
    return filtriraj_zalbe_po_kategoriji(
        kategorija,
        limit=limit
    )


@router.get(
    "/count/all",
    summary="Prebroj sve žalbe"
)
def prebroj_sve_zalbe():
    return {
        "count": count_zalbe()
    }


@router.post(
    "/search/filter",
    response_model=List[ZalbaSearchResult],
    summary="Vektorska pretraga + filter"
)
def slozen_upit_filter(
        zahtev: ZalbaSearchRequest
):
    return pretrazi_slicne_zalbe_po_kategoriji_i_prioritetu(
        tekst_upita=zahtev.tekst_upita,
        kategorija=zahtev.kategorija,
        prioritet=zahtev.prioritet,
        top_k=zahtev.top_k
    )


@router.post(
    "/search/iterator",
    response_model=List[ZalbaSearchResult],
    summary="Vektorska pretraga + iterator"
)
def slozen_upit_iterator(
        zahtev: ZalbaIteratorRequest
):
    return pretrazi_slicne_zalbe_po_timu_iterator(
        tekst_upita=zahtev.tekst_upita,
        tim=zahtev.tim,
        batch_size=zahtev.batch_size
    )


@router.post(
    "/search/hybrid",
    response_model=List[ZalbaSearchResult],
    summary="Hibridna pretraga"
)
def slozen_upit_hibridna(
        zahtev: ZalbaHybridRequest
):
    return hibridna_pretraga_po_naslovu_i_opisu(
        tekst_upita=zahtev.tekst_upita,
        top_k=zahtev.top_k
    )


@router.get(
    "/",
    response_model=List[ZalbaResponse],
    summary="Dohvati sve žalbe"
)
def dohvati_sve_zalbe(
        limit: int = 100
):
    return get_all_zalbe(limit=limit)


@router.get(
    "/{zalba_id}",
    response_model=ZalbaResponse,
    summary="Dohvati žalbu po ID-u"
)
def dohvati_zalbu(
        zalba_id: int
):
    zalba = get_zalba_by_id(zalba_id)

    if not zalba:
        raise HTTPException(
            status_code=404,
            detail=f"Žalba ID={zalba_id} ne postoji"
        )

    return zalba


@router.put(
    "/{zalba_id}",
    response_model=dict,
    summary="Ažuriraj žalbu"
)
def azuriraj_zalbu(
        zalba_id: int,
        izmene: ZalbaUpdate
):
    nova_polja = izmene.model_dump(
        exclude_unset=True
    )

    if not nova_polja:
        raise HTTPException(
            status_code=400,
            detail="Nema polja za ažuriranje"
        )

    rezultat = update_zalba(
        zalba_id,
        nova_polja
    )

    if rezultat.get("status") == "error":
        raise HTTPException(
            status_code=404,
            detail=rezultat.get(
                "message",
                "Žalba ne postoji"
            )
        )

    return rezultat


@router.delete(
    "/{zalba_id}",
    response_model=dict,
    summary="Obriši žalbu"
)
def obrisi_zalbu(
        zalba_id: int
):
    if not get_zalba_by_id(zalba_id):
        raise HTTPException(
            status_code=404,
            detail=f"Žalba ID={zalba_id} ne postoji"
        )

    return delete_zalba(zalba_id)