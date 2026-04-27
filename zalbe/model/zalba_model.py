
from pydantic import BaseModel, Field
from typing import Optional


class ZalbaCreate(BaseModel):
    """Model za kreiranje nove žalbe (POST)."""
    zalba_id: int = Field(..., description="Jedinstveni ID žalbe", example=9999)
    naslov: str = Field(..., max_length=200, example="Klima ne radi u sobi")
    opis: str = Field(..., max_length=1000, example="Klima uređaj u hotelskoj sobi nije radio tokom celog boravka.")
    kategorija: str = Field(..., max_length=50, example="smestaj")
    tim: str = Field(..., max_length=50, example="Tim za smestaj")
    prioritet: int = Field(..., ge=1, le=2, description="1=normalna, 2=hitna", example=2)
    id_ture: int = Field(..., example=1234)


class ZalbaUpdate(BaseModel):
    """Model za ažuriranje žalbe (PUT). Sva polja su opciona."""
    naslov: Optional[str] = Field(None, max_length=200)
    opis: Optional[str] = Field(None, max_length=1000)
    kategorija: Optional[str] = Field(None, max_length=50)
    tim: Optional[str] = Field(None, max_length=50)
    prioritet: Optional[int] = Field(None, ge=1, le=2)
    id_ture: Optional[int] = None


class ZalbaResponse(BaseModel):
    """Model za odgovor (GET) - bez vektorskih polja."""
    zalba_id: int
    naslov: str
    opis: str
    kategorija: str
    tim: str
    prioritet: int
    id_ture: int


class ZalbaSearchRequest(BaseModel):
    """Model za vektorsku pretragu - filter + 2 uslova."""
    tekst_upita: str = Field(..., example="klima ne radi u sobi")
    kategorija: str = Field(..., example="smestaj")
    prioritet: int = Field(..., ge=1, le=2, example=2)
    top_k: int = Field(5, ge=1, le=50)


class ZalbaIteratorRequest(BaseModel):
    """Model za vektorsku pretragu sa iteratorom."""
    tekst_upita: str = Field(..., example="kasnio mi je avion")
    tim: str = Field(..., example="Tim za prevoz")
    batch_size: int = Field(20, ge=1, le=100)


class ZalbaHybridRequest(BaseModel):
    """Model za hibridnu pretragu."""
    tekst_upita: str = Field(..., example="problem sa hotelskom sobom")
    top_k: int = Field(5, ge=1, le=50)


class ZalbaSearchResult(BaseModel):
    """Rezultat vektorske pretrage (sa distance poljem)."""
    zalba_id: int
    naslov: str
    opis: Optional[str] = None
    kategorija: str
    tim: str
    prioritet: int
    distance: float