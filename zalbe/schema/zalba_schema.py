
from pymilvus import DataType, MilvusClient
from config import EMBEDDING_DIM, NLIST


def zalba_schema(client: MilvusClient):
    """Definiše šemu kolekcije zalbe."""
    schema = client.create_schema(
        auto_id=False,
        enable_dynamic_fields=False,
        description="Žalbe putnika sa vektorskom reprezentacijom naslova i opisa"
    )

    # Osnovna polja (nevektorizovana)
    schema.add_field("zalba_id", DataType.INT64, is_primary=True)
    schema.add_field("naslov", DataType.VARCHAR, max_length=200)
    schema.add_field("opis", DataType.VARCHAR, max_length=1000)
    schema.add_field("kategorija", DataType.VARCHAR, max_length=50)
    schema.add_field("tim", DataType.VARCHAR, max_length=50)
    schema.add_field("prioritet", DataType.INT64)
    schema.add_field("id_ture", DataType.INT64)

    # Dva vektorska polja (za hibridnu pretragu)
    schema.add_field("naslov_vector", DataType.FLOAT_VECTOR, dim=EMBEDDING_DIM)
    schema.add_field("opis_vector", DataType.FLOAT_VECTOR, dim=EMBEDDING_DIM)

    return schema


def zalba_index_params(client: MilvusClient):
    """Definiše indekse za kolekciju zalbe."""
    idx = client.prepare_index_params()

    idx.add_index("zalba_id")
    idx.add_index("kategorija", index_type="INVERTED")
    idx.add_index("tim", index_type="INVERTED")
    idx.add_index("prioritet", index_type="STL_SORT")

    # Indeksi nad oba vektorska polja
    idx.add_index(
        "naslov_vector",
        index_type="IVF_FLAT",
        metric_type="COSINE",
        params={"nlist": NLIST},
    )
    idx.add_index(
        "opis_vector",
        index_type="IVF_FLAT",
        metric_type="COSINE",
        params={"nlist": NLIST},
    )

    return idx