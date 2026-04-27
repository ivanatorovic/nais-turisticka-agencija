
from pymilvus import DataType, MilvusClient
from config import EMBEDDING_DIM, NLIST


def ocena_schema(client: MilvusClient):
    """Definiše šemu kolekcije ocene_resavanja."""
    schema = client.create_schema(
        auto_id=False,
        enable_dynamic_fields=False,
        description="Ocene i komentari putnika nakon rešavanja žalbi"
    )

    # Osnovna polja (nevektorizovana)
    schema.add_field("ocena_id", DataType.INT64, is_primary=True)
    schema.add_field("zalba_id", DataType.INT64)
    schema.add_field("ocena", DataType.INT64)
    schema.add_field("tim", DataType.VARCHAR, max_length=50)
    schema.add_field("komentar", DataType.VARCHAR, max_length=500)

    # Vektorizovano polje
    schema.add_field("komentar_vector", DataType.FLOAT_VECTOR, dim=EMBEDDING_DIM)

    return schema


def ocena_index_params(client: MilvusClient):
    """Definiše indekse za kolekciju ocene_resavanja."""
    idx = client.prepare_index_params()

    idx.add_index("ocena_id")
    idx.add_index("zalba_id", index_type="INVERTED")
    idx.add_index("tim", index_type="INVERTED")
    idx.add_index("ocena", index_type="STL_SORT")

    idx.add_index(
        "komentar_vector",
        index_type="IVF_FLAT",
        metric_type="COSINE",
        params={"nlist": NLIST},
    )

    return idx