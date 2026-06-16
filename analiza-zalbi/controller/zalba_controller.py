from typing import List

from fastapi import APIRouter, HTTPException, Query

from model.zalba_model import (
    ZalbaCreate,
    ZalbaUpdate,
    ZalbaResponse,
)

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


router = APIRouter(
    prefix="/zalbe",
    tags=["Analiza žalbi"]
)



@router.post(
    "/",
    response_model=ZalbaResponse,
    summary="Kreiraj novu žalbu u Cassandra bazi"
)
def create_zalba(zalba: ZalbaCreate):
    """
    Kreira novu žalbu i upisuje je u sve odgovarajuće
    denormalizovane Cassandra tabele.
    """

    return create_zalba_service(zalba)



@router.get(
    "/izvestaj/tim/{tim}",
    response_model=List[ZalbaResponse],
    summary="Prosta sekcija: žalbe izabranog tima i prioriteta"
)
def get_report_complaints_by_team(
        tim: str,
        minimalni_prioritet: int = Query(
            default=1,
            ge=1,
            le=2,
            description=(
                    "Minimalni prioritet žalbe: "
                    "1 = normalna, 2 = hitna"
            )
        ),
        limit: int = Query(
            default=100,
            ge=1,
            le=1000,
            description="Maksimalan broj vraćenih žalbi"
        )
):
    """
    Prosta sekcija generatora izveštaja.

    Dobavlja žalbe koje su dodeljene prosleđenom timu,
    a zatim zadržava samo žalbe čiji je prioritet veći
    ili jednak prosleđenom minimalnom prioritetu.

    Primer:
    tim = Tim za smestaj
    minimalni_prioritet = 2

    Rezultat sadrži samo hitne žalbe tima za smeštaj.
    """

    zalbe = get_zalbe_by_team_service(tim)

    filtrirane_zalbe = [
        zalba
        for zalba in zalbe
        if zalba.get("prioritet", 0) >= minimalni_prioritet
    ]

    return filtrirane_zalbe[:limit]



@router.get(
    "/kategorija/{kategorija}",
    response_model=List[ZalbaResponse],
    summary="Dohvati žalbe po kategoriji"
)
def get_by_category(kategorija: str):
    """
    Dobavlja žalbe iz tabele complaints_by_category.
    """

    return get_zalbe_by_category_service(kategorija)


@router.get(
    "/tim/{tim}",
    response_model=List[ZalbaResponse],
    summary="Dohvati žalbe po timu"
)
def get_by_team(tim: str):
    """
    Dobavlja sve žalbe iz tabele complaints_by_team
    za prosleđeni tim.
    """

    return get_zalbe_by_team_service(tim)


@router.get(
    "/prioritet/{prioritet}",
    response_model=List[ZalbaResponse],
    summary="Dohvati žalbe po prioritetu"
)
def get_by_priority(
        prioritet: int
):
    """
    Dobavlja žalbe iz tabele complaints_by_priority.
    """

    if prioritet not in (1, 2):
        raise HTTPException(
            status_code=400,
            detail=(
                "Prioritet mora biti 1 ili 2. "
                "1 = normalna, 2 = hitna žalba."
            )
        )

    return get_zalbe_by_priority_service(prioritet)


@router.get(
    "/tura/{id_ture}",
    response_model=List[ZalbaResponse],
    summary="Dohvati žalbe po turi"
)
def get_by_tour(id_ture: int):
    """
    Dobavlja žalbe iz tabele complaints_by_tour.
    """

    return get_zalbe_by_tour_service(id_ture)



@router.get(
    "/count/kategorija/{kategorija}",
    summary="Prebroj žalbe određene kategorije"
)
def count_by_category(kategorija: str):
    """
    Vraća ukupan broj žalbi u prosleđenoj kategoriji.
    """

    return count_by_category_service(kategorija)


@router.get(
    "/count/tim/{tim}",
    summary="Prebroj žalbe određenog tima"
)
def count_by_team(tim: str):
    """
    Vraća ukupan broj žalbi dodeljenih prosleđenom timu.
    """

    return count_by_team_service(tim)


@router.get(
    "/count/prioritet/{prioritet}",
    summary="Prebroj žalbe određenog prioriteta"
)
def count_by_priority(prioritet: int):
    """
    Vraća ukupan broj žalbi prosleđenog prioriteta.
    """

    if prioritet not in (1, 2):
        raise HTTPException(
            status_code=400,
            detail=(
                "Prioritet mora biti 1 ili 2. "
                "1 = normalna, 2 = hitna žalba."
            )
        )

    return count_by_priority_service(prioritet)




@router.get(
    "/{zalba_id}",
    response_model=ZalbaResponse,
    summary="Dohvati žalbu po ID-u"
)
def get_zalba(zalba_id: int):
    """
    Dobavlja jednu žalbu na osnovu njenog jedinstvenog ID-a.
    """

    result = get_zalba_by_id_service(zalba_id)

    if result is None:
        raise HTTPException(
            status_code=404,
            detail="Žalba nije pronađena"
        )

    return result



@router.put(
    "/{zalba_id}",
    response_model=ZalbaResponse,
    summary="Ažuriraj postojeću žalbu"
)
def update_zalba(
        zalba_id: int,
        zalba: ZalbaUpdate
):
    """
    Ažurira žalbu u svim denormalizovanim Cassandra tabelama.
    """

    result = update_zalba_service(
        zalba_id,
        zalba
    )

    if result is None:
        raise HTTPException(
            status_code=404,
            detail="Žalba nije pronađena"
        )

    return result



@router.delete(
    "/{zalba_id}",
    summary="Obriši žalbu"
)
def delete_zalba(zalba_id: int):
    """
    Briše žalbu iz svih denormalizovanih Cassandra tabela.
    """

    deleted = delete_zalba_service(zalba_id)

    if not deleted:
        raise HTTPException(
            status_code=404,
            detail="Žalba nije pronađena"
        )

    return {
        "message": "Žalba uspešno obrisana",
        "zalba_id": zalba_id
    }