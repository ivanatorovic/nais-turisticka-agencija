from pydantic import BaseModel, Field
from typing import Optional


class OcenaCreate(BaseModel):
    """Model za kreiranje nove ocene."""
    ocena_id: int = Field(..., example=9999)
    zalba_id: int = Field(..., example=1)
    ocena: int = Field(..., ge=1, le=5, example=5)
    tim: str = Field(..., max_length=50, example="Tim za smestaj")
    komentar: str = Field(..., max_length=500, example="Sjajno iskustvo, brzo su mi pomogli!")


class OcenaUpdate(BaseModel):
    """Model za ažuriranje ocene."""
    ocena: Optional[int] = Field(None, ge=1, le=5)
    tim: Optional[str] = Field(None, max_length=50)
    komentar: Optional[str] = Field(None, max_length=500)


class OcenaResponse(BaseModel):
    """Model za odgovor (GET)."""
    ocena_id: int
    zalba_id: int
    ocena: int
    tim: str
    komentar: str


class CountResponse(BaseModel):
    """Odgovor za prebrojavanje."""
    filter_expr: str
    count: int