
import logging
from config import OCENE_COLLECTION
from services.milvus_service import milvus_service
from services.embedding_service import embedding_service

logger = logging.getLogger(__name__)



def create_ocena(ocena: dict) -> dict:
    
    komentar_vector = embedding_service.encode_one(ocena["komentar"])
    record = {**ocena, "komentar_vector": komentar_vector}
    
    result = milvus_service.insert(OCENE_COLLECTION, [record])
    logger.info("Dodata ocena ID=%d", ocena["ocena_id"])
    return {"status": "success", "ocena_id": ocena["ocena_id"]}



def get_ocena_by_id(ocena_id: int) -> dict | None:
    """Dohvata jednu ocenu po ID-u."""
    client = milvus_service.client
    result = client.get(
        collection_name=OCENE_COLLECTION,
        ids=[ocena_id],
        output_fields=["ocena_id", "zalba_id", "ocena", "tim", "komentar"]
    )
    if not result:
        return None
    return result[0]


def get_all_ocene(limit: int = 100) -> list[dict]:
   
    client = milvus_service.client
    result = client.query(
        collection_name=OCENE_COLLECTION,
        filter="",
        output_fields=["ocena_id", "zalba_id", "ocena", "tim", "komentar"],
        limit=limit
    )
    return result


def get_ocene_by_filter(filter_expr: str, limit: int = 100) -> list[dict]:
    
    client = milvus_service.client
    result = client.query(
        collection_name=OCENE_COLLECTION,
        filter=filter_expr,
        output_fields=["ocena_id", "zalba_id", "ocena", "tim", "komentar"],
        limit=limit
    )
    return result


def count_ocene(filter_expr: str = "") -> int:
    """Prebrojava ocene."""
    client = milvus_service.client
    if filter_expr:
        result = client.query(
            collection_name=OCENE_COLLECTION,
            filter=filter_expr,
            output_fields=["count(*)"]
        )
        return result[0].get("count(*)", 0) if result else 0
    return milvus_service.count(OCENE_COLLECTION)



def update_ocena(ocena_id: int, nova_polja: dict) -> dict:
    """Ažurira postojeću ocenu."""
    trenutna = get_ocena_by_id(ocena_id)
    if not trenutna:
        return {"status": "error", "message": f"Ocena ID={ocena_id} ne postoji"}
    
    azurirana = {**trenutna, **nova_polja}
    azurirana["komentar_vector"] = embedding_service.encode_one(azurirana["komentar"])
    
    client = milvus_service.client
    client.upsert(collection_name=OCENE_COLLECTION, data=[azurirana])
    logger.info("Ažurirana ocena ID=%d", ocena_id)
    return {"status": "success", "ocena_id": ocena_id}



def delete_ocena(ocena_id: int) -> dict:
    """Briše ocenu po ID-u."""
    client = milvus_service.client
    client.delete(
        collection_name=OCENE_COLLECTION,
        ids=[ocena_id]
    )
    logger.info("Obrisana ocena ID=%d", ocena_id)
    return {"status": "success", "deleted_id": ocena_id}


def delete_ocene_by_filter(filter_expr: str) -> dict:
    """Briše ocene po filteru."""
    client = milvus_service.client
    result = client.delete(
        collection_name=OCENE_COLLECTION,
        filter=filter_expr
    )
    logger.info("Obrisane ocene po filteru: %s", filter_expr)
    return {"status": "success", "filter": filter_expr, "result": result}



def prebroj_ocene_po_uslovu(filter_expr: str) -> int:
   
    logger.info("Prebrojavam ocene po uslovu: %s", filter_expr)
    client = milvus_service.client
    result = client.query(
        collection_name=OCENE_COLLECTION,
        filter=filter_expr,
        output_fields=["count(*)"]
    )
    broj = result[0].get("count(*)", 0) if result else 0
    logger.info("  Pronađeno: %d slogova", broj)
    return broj