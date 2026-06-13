from pymilvus import DataType, MilvusClient

from config import EMBEDDING_DIM, NLIST


def zalba_schema(client: MilvusClient):
    schema = client.create_schema(
        auto_id=False,
        enable_dynamic_fields=False,
        description=(
            "Žalbe putnika sa vektorskom reprezentacijom "
            "naslova i opisa"
        )
    )

    # Osnovna polja žalbe
    schema.add_field(
        field_name="zalba_id",
        datatype=DataType.INT64,
        is_primary=True
    )

    schema.add_field(
        field_name="naslov",
        datatype=DataType.VARCHAR,
        max_length=200
    )

    schema.add_field(
        field_name="opis",
        datatype=DataType.VARCHAR,
        max_length=1000
    )

    schema.add_field(
        field_name="kategorija",
        datatype=DataType.VARCHAR,
        max_length=50
    )

    schema.add_field(
        field_name="tim",
        datatype=DataType.VARCHAR,
        max_length=50
    )

    schema.add_field(
        field_name="prioritet",
        datatype=DataType.INT64
    )

    schema.add_field(
        field_name="id_ture",
        datatype=DataType.INT64
    )

    # Podaci potrebni za žalbu na dodatnu aktivnost
    schema.add_field(
        field_name="aktivnost_id",
        datatype=DataType.INT64
    )
    schema.add_field(
        field_name="putnik_id",
        datatype=DataType.INT64
    )

    # Jedinstveni identifikator Saga transakcije
    schema.add_field(
        field_name="saga_id",
        datatype=DataType.VARCHAR,
        max_length=100
    )

    # PENDING, CONFIRMED, CANCELLED ili NOT_APPLICABLE
    schema.add_field(
        field_name="saga_status",
        datatype=DataType.VARCHAR,
        max_length=30
    )

    # Vektorska polja
    schema.add_field(
        field_name="naslov_vector",
        datatype=DataType.FLOAT_VECTOR,
        dim=EMBEDDING_DIM
    )

    schema.add_field(
        field_name="opis_vector",
        datatype=DataType.FLOAT_VECTOR,
        dim=EMBEDDING_DIM
    )

    return schema


def zalba_index_params(client: MilvusClient):
    """Definiše indekse za kolekciju žalbi."""

    idx = client.prepare_index_params()

    # Indeks primarnog ključa
    idx.add_index(
        field_name="zalba_id"
    )

    # Indeksi za filtriranje
    idx.add_index(
        field_name="kategorija",
        index_type="INVERTED"
    )

    idx.add_index(
        field_name="tim",
        index_type="INVERTED"
    )

    idx.add_index(
        field_name="prioritet",
        index_type="STL_SORT"
    )

    idx.add_index(
        field_name="aktivnost_id",
        index_type="STL_SORT"
    )

    idx.add_index(
        field_name="saga_status",
        index_type="INVERTED"
    )

    # Indeks nad vektorom naslova
    idx.add_index(
        field_name="naslov_vector",
        index_type="IVF_FLAT",
        metric_type="COSINE",
        params={"nlist": NLIST}
    )

    # Indeks nad vektorom opisa
    idx.add_index(
        field_name="opis_vector",
        index_type="IVF_FLAT",
        metric_type="COSINE",
        params={"nlist": NLIST}
    )

    return idx