from pydantic import BaseModel, Field
from typing import Optional


class ZalbaCreate(BaseModel):
    zalba_id: int = Field(..., description="Jedinstveni ID žalbe", example=9999)
    naslov: str = Field(..., max_length=200, example="Klima ne radi u sobi")
    opis: str = Field(..., max_length=1000, example="Klima uređaj u hotelskoj sobi nije radio tokom celog boravka.")
    kategorija: str = Field(..., max_length=50, example="smestaj")
    tim: str = Field(..., max_length=50, example="Tim za smestaj")
    prioritet: int = Field(..., ge=1, le=2, description="1=normalna, 2=hitna", example=2)
    id_ture: int = Field(..., example=1234)


class ZalbaUpdate(BaseModel):
    naslov: Optional[str] = Field(None, max_length=200)
    opis: Optional[str] = Field(None, max_length=1000)
    kategorija: Optional[str] = Field(None, max_length=50)
    tim: Optional[str] = Field(None, max_length=50)
    prioritet: Optional[int] = Field(None, ge=1, le=2)
    id_ture: Optional[int] = None


class ZalbaResponse(BaseModel):
    zalba_id: int
    naslov: str
    opis: str
    kategorija: str
    tim: str
    prioritet: int
    id_ture: int