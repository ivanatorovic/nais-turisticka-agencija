
import requests

from fastapi import (
    APIRouter,
    HTTPException,
    Query,
)

from service.izvestaj_service import (
    formiraj_pregled_izvestaja
)


router = APIRouter(
    prefix="/izvestaj",
    tags=["Generator izveštaja"]
)


@router.get(
    "/pregled",
    summary="Formiraj objedinjeni pregled izveštaja"
)
def pregled_izvestaja(
        tim: str = Query(
            default="Tim za smestaj",
            description=(
                    "Tim za Cassandra prostu sekciju"
            )
        ),
        minimalni_prioritet: int = Query(
            default=1,
            ge=1,
            description=(
                    "Minimalni prioritet za Cassandra sekciju"
            )
        ),
        kategorija: str = Query(
            default="smestaj",
            description=(
                    "Kategorija za Milvus prostu i složenu sekciju"
            )
        ),
        tekst_upita: str = Query(
            default=(
                    "Klima u hotelskoj sobi nije radila "
                    "tokom boravka"
            ),
            min_length=3,
            description=(
                    "Opis problema za semantičku pretragu"
            )
        ),
        prioritet: int = Query(
            default=2,
            ge=1,
            description=(
                    "Prioritet za složeni Milvus upit"
            )
        ),
        minimalna_slicnost: float = Query(
            default=0.80,
            ge=0,
            le=1,
            description=(
                    "Minimalna COSINE sličnost. "
                    "Vrednost 0.80 predstavlja 80 procenata."
            )
        ),
        limit: int = Query(
            default=100,
            ge=1,
            le=1000
        ),
        top_k: int = Query(
            default=20,
            ge=1,
            le=100
        )
):
    try:
        return formiraj_pregled_izvestaja(
            tim=tim,
            minimalni_prioritet=minimalni_prioritet,
            kategorija=kategorija,
            tekst_upita=tekst_upita,
            prioritet=prioritet,
            minimalna_slicnost=minimalna_slicnost,
            limit=limit,
            top_k=top_k
        )

    except requests.exceptions.RequestException as exception:
        raise HTTPException(
            status_code=503,
            detail=(
                "Jedan od servisa sa podacima nije dostupan: "
                f"{str(exception)}"
            )
        ) from exception

    except Exception as exception:
        raise HTTPException(
            status_code=500,
            detail=(
                "Greška pri formiranju izveštaja: "
                f"{str(exception)}"
            )
        ) from exception

