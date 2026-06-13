from typing import Optional

from pydantic import BaseModel, Field


class ZalbaCreate(BaseModel):
    """Model za kreiranje obične žalbe."""

    zalba_id: int = Field(
        ...,
        description="Jedinstveni ID žalbe",
        example=9999
    )
    naslov: str = Field(
        ...,
        max_length=200,
        example="Klima ne radi u sobi"
    )
    opis: str = Field(
        ...,
        max_length=1000,
        example="Klima uređaj u hotelskoj sobi nije radio tokom celog boravka."
    )
    kategorija: str = Field(
        ...,
        max_length=50,
        example="smestaj"
    )
    tim: str = Field(
        ...,
        max_length=50,
        example="Tim za smestaj"
    )
    prioritet: int = Field(
        ...,
        ge=1,
        le=2,
        description="1=normalna, 2=hitna",
        example=2
    )
    id_ture: int = Field(
        ...,
        example=1234
    )


class ZalbaZaAktivnostCreate(BaseModel):
    """
    Model za podnošenje žalbe na dodatnu aktivnost.

    Korisnik prosleđuje aktivnost_id, dok backend automatski
    generiše saga_id i postavlja početni Saga status.
    """

    zalba_id: int = Field(
        ...,
        description="Jedinstveni ID žalbe",
        example=10001
    )
    aktivnost_id: int = Field(
        ...,
        description="ID dodatne aktivnosti na koju se žalba odnosi",
        example=15
    )
    putnik_id: int = Field(
        ...,
        description="ID putnika koji je podneo žalbu",
        example=7
    )
    naslov: str = Field(
        ...,
        max_length=200,
        example="Dodatna aktivnost nije održana"
    )
    opis: str = Field(
        ...,
        max_length=1000,
        example="Izlet brodom je otkazan bez prethodnog obaveštenja."
    )
    prioritet: int = Field(
        default=1,
        ge=1,
        le=2,
        description="1=normalna, 2=hitna",
        example=1
    )
    id_ture: int = Field(
        ...,
        description="ID ture kojoj pripada dodatna aktivnost",
        example=1234
    )


class ZalbaUpdate(BaseModel):
    """Model za izmenu žalbe."""

    naslov: Optional[str] = Field(
        default=None,
        max_length=200
    )
    opis: Optional[str] = Field(
        default=None,
        max_length=1000
    )
    kategorija: Optional[str] = Field(
        default=None,
        max_length=50
    )
    tim: Optional[str] = Field(
        default=None,
        max_length=50
    )
    prioritet: Optional[int] = Field(
        default=None,
        ge=1,
        le=2
    )
    id_ture: Optional[int] = None
    aktivnost_id: Optional[int] = None
    saga_status: Optional[str] = Field(
        default=None,
        max_length=30
    )


class ZalbaResponse(BaseModel):
    """Podaci koji se vraćaju prilikom dobavljanja žalbe."""

    zalba_id: int
    naslov: str
    opis: str
    kategorija: str
    tim: str
    prioritet: int
    id_ture: int

    aktivnost_id: Optional[int] = None
    saga_id: Optional[str] = None
    saga_status: Optional[str] = None


class ZalbaSearchRequest(BaseModel):
    """Model za vektorsku pretragu uz kategoriju i prioritet."""

    tekst_upita: str = Field(
        ...,
        example="klima ne radi u sobi"
    )
    kategorija: str = Field(
        ...,
        example="smestaj"
    )
    prioritet: int = Field(
        ...,
        ge=1,
        le=2,
        example=2
    )
    top_k: int = Field(
        default=5,
        ge=1,
        le=50
    )


class ZalbaIteratorRequest(BaseModel):
    """Model za vektorsku pretragu sa iteratorom."""

    tekst_upita: str = Field(
        ...,
        example="kasnio mi je avion"
    )
    tim: str = Field(
        ...,
        example="Tim za prevoz"
    )
    batch_size: int = Field(
        default=20,
        ge=1,
        le=100
    )


class ZalbaHybridRequest(BaseModel):
    """Model za hibridnu pretragu."""

    tekst_upita: str = Field(
        ...,
        example="problem sa hotelskom sobom"
    )
    top_k: int = Field(
        default=5,
        ge=1,
        le=50
    )


class ZalbaSearchResult(BaseModel):
    """Rezultat vektorske pretrage sa merom sličnosti."""

    zalba_id: int
    naslov: str
    opis: Optional[str] = None
    kategorija: str
    tim: str
    prioritet: int
    distance: float