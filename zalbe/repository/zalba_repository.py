
import logging
from pymilvus import AnnSearchRequest, RRFRanker
from config import ZALBE_COLLECTION, DEFAULT_TOP_K, NPROBE
from services.milvus_service import milvus_service
from services.embedding_service import embedding_service

logger = logging.getLogger(__name__)



def create_zalba(zalba: dict) -> dict:
    """Dodaje novu žalbu. Vektorizuje naslov i opis."""
    naslov_vector = embedding_service.encode_one(zalba["naslov"])
    opis_vector = embedding_service.encode_one(zalba["opis"])
    
    record = {
        **zalba,
        "naslov_vector": naslov_vector,
        "opis_vector": opis_vector
    }
    
    result = milvus_service.insert(ZALBE_COLLECTION, [record])
    logger.info("Dodata žalba ID=%d", zalba["zalba_id"])
    return {"status": "success", "zalba_id": zalba["zalba_id"]}



def get_zalba_by_id(zalba_id: int) -> dict | None:
    """Dohvata jednu žalbu po ID-u."""
    client = milvus_service.client
    result = client.get(
        collection_name=ZALBE_COLLECTION,
        ids=[zalba_id],
        output_fields=["zalba_id", "naslov", "opis", "kategorija", "tim", "prioritet", "id_ture"]
    )
    if not result:
        return None
    return result[0]


def get_all_zalbe(limit: int = 100) -> list[dict]:
    """Dohvata sve žalbe (sa limitom)."""
    client = milvus_service.client
    result = client.query(
        collection_name=ZALBE_COLLECTION,
        filter="",
        output_fields=["zalba_id", "naslov", "opis", "kategorija", "tim", "prioritet", "id_ture"],
        limit=limit
    )
    return result


def get_zalbe_by_filter(filter_expr: str, limit: int = 100) -> list[dict]:
    """Dohvata žalbe po filteru."""
    client = milvus_service.client
    result = client.query(
        collection_name=ZALBE_COLLECTION,
        filter=filter_expr,
        output_fields=["zalba_id", "naslov", "opis", "kategorija", "tim", "prioritet", "id_ture"],
        limit=limit
    )
    return result


def count_zalbe(filter_expr: str = "") -> int:
    """Prebrojava žalbe."""
    client = milvus_service.client
    if filter_expr:
        result = client.query(
            collection_name=ZALBE_COLLECTION,
            filter=filter_expr,
            output_fields=["count(*)"]
        )
        return result[0].get("count(*)", 0) if result else 0
    return milvus_service.count(ZALBE_COLLECTION)



def update_zalba(zalba_id: int, nova_polja: dict) -> dict:
    """Ažurira postojeću žalbu. Re-vektorizuje naslov i opis."""
    trenutna = get_zalba_by_id(zalba_id)
    if not trenutna:
        return {"status": "error", "message": f"Žalba ID={zalba_id} ne postoji"}
    
    azurirana = {**trenutna, **nova_polja}
    azurirana["naslov_vector"] = embedding_service.encode_one(azurirana["naslov"])
    azurirana["opis_vector"] = embedding_service.encode_one(azurirana["opis"])
    
    client = milvus_service.client
    client.upsert(collection_name=ZALBE_COLLECTION, data=[azurirana])
    logger.info("Ažurirana žalba ID=%d", zalba_id)
    return {"status": "success", "zalba_id": zalba_id}



def delete_zalba(zalba_id: int) -> dict:
    """Briše žalbu po ID-u."""
    client = milvus_service.client
    client.delete(
        collection_name=ZALBE_COLLECTION,
        ids=[zalba_id]
    )
    logger.info("Obrisana žalba ID=%d", zalba_id)
    return {"status": "success", "deleted_id": zalba_id}


def delete_zalbe_by_filter(filter_expr: str) -> dict:
    """Briše više žalbi po filteru."""
    client = milvus_service.client
    result = client.delete(
        collection_name=ZALBE_COLLECTION,
        filter=filter_expr
    )
    logger.info("Obrisane žalbe po filteru: %s", filter_expr)
    return {"status": "success", "filter": filter_expr, "result": result}


# ============================================================
# UPITI - PROSTI
# ============================================================
def filtriraj_zalbe_po_kategoriji(kategorija: str, limit: int = 10) -> list[dict]:
    """PROST UPIT: Filtriranje žalbi po kategoriji."""
    logger.info("Filtriram žalbe po kategoriji='%s'", kategorija)
    client = milvus_service.client
    result = client.query(
        collection_name=ZALBE_COLLECTION,
        filter=f'kategorija == "{kategorija}"',
        output_fields=["zalba_id", "naslov", "kategorija", "tim", "prioritet"],
        limit=limit
    )
    return result


# ============================================================
# UPITI - SLOŽENI
# ============================================================
def pretrazi_slicne_zalbe_po_kategoriji_i_prioritetu(
    tekst_upita: str,
    kategorija: str,
    prioritet: int,
    top_k: int = DEFAULT_TOP_K
) -> list[dict]:
    """SLOŽEN UPIT: Vektorska pretraga + filter (2 uslova)."""
    logger.info("Vektorska + filter: tekst='%s', kategorija='%s', prioritet=%d",
                tekst_upita, kategorija, prioritet)
    
    upit_vektor = embedding_service.encode_one(tekst_upita)
    
    client = milvus_service.client
    result = client.search(
        collection_name=ZALBE_COLLECTION,
        data=[upit_vektor],
        anns_field="opis_vector",
        filter=f'kategorija == "{kategorija}" and prioritet == {prioritet}',
        limit=top_k,
        output_fields=["zalba_id", "naslov", "opis", "kategorija", "tim", "prioritet"],
        search_params={"metric_type": "COSINE", "params": {"nprobe": NPROBE}}
    )
    
    return [
        {
            "zalba_id": hit["entity"].get("zalba_id"),
            "naslov": hit["entity"].get("naslov"),
            "opis": hit["entity"].get("opis"),
            "kategorija": hit["entity"].get("kategorija"),
            "tim": hit["entity"].get("tim"),
            "prioritet": hit["entity"].get("prioritet"),
            "distance": hit["distance"],
        }
        for hit in result[0]
    ]


def pretrazi_slicne_zalbe_po_timu_iterator(
    tekst_upita: str,
    tim: str,
    batch_size: int = 20
) -> list[dict]:
    """SLOŽEN UPIT: Vektorska pretraga sa filterom uz korišćenje iteratora."""
    logger.info("Vektorska + iterator: tekst='%s', tim='%s', batch_size=%d",
                tekst_upita, tim, batch_size)
    
    upit_vektor = embedding_service.encode_one(tekst_upita)
    
    client = milvus_service.client
    iterator = client.search_iterator(
        collection_name=ZALBE_COLLECTION,
        data=[upit_vektor],
        anns_field="opis_vector",
        filter=f'tim == "{tim}"',
        batch_size=batch_size,
        output_fields=["zalba_id", "naslov", "kategorija", "tim", "prioritet"],
        search_params={"metric_type": "COSINE", "params": {"nprobe": NPROBE}}
    )
    
    svi_rezultati = []
    batch_broj = 0
    while True:
        batch = iterator.next()
        if not batch:
            iterator.close()
            break
        batch_broj += 1
        logger.info("  Batch %d: %d slogova", batch_broj, len(batch))
        for hit in batch:
            svi_rezultati.append({
                "zalba_id": hit.entity.get("zalba_id"),
                "naslov": hit.entity.get("naslov"),
                "kategorija": hit.entity.get("kategorija"),
                "tim": hit.entity.get("tim"),
                "prioritet": hit.entity.get("prioritet"),
                "distance": hit.distance,
            })
    
    logger.info("  Ukupno %d slogova u %d batch-eva", len(svi_rezultati), batch_broj)
    return svi_rezultati


def hibridna_pretraga_po_naslovu_i_opisu(
    tekst_upita: str,
    top_k: int = DEFAULT_TOP_K
) -> list[dict]:
    """SLOŽEN UPIT: Hibridna pretraga (naslov + opis)."""
    logger.info("Hibridna pretraga: tekst='%s'", tekst_upita)
    
    upit_vektor = embedding_service.encode_one(tekst_upita)
    
    request_naslov = AnnSearchRequest(
        data=[upit_vektor], anns_field="naslov_vector",
        param={"metric_type": "COSINE", "params": {"nprobe": NPROBE}}, limit=top_k
    )
    request_opis = AnnSearchRequest(
        data=[upit_vektor], anns_field="opis_vector",
        param={"metric_type": "COSINE", "params": {"nprobe": NPROBE}}, limit=top_k
    )
    
    client = milvus_service.client
    result = client.hybrid_search(
        collection_name=ZALBE_COLLECTION,
        reqs=[request_naslov, request_opis],
        ranker=RRFRanker(60),
        limit=top_k,
        output_fields=["zalba_id", "naslov", "opis", "kategorija", "tim", "prioritet"]
    )
    
    return [
        {
            "zalba_id": hit["entity"].get("zalba_id"),
            "naslov": hit["entity"].get("naslov"),
            "opis": hit["entity"].get("opis"),
            "kategorija": hit["entity"].get("kategorija"),
            "tim": hit["entity"].get("tim"),
            "prioritet": hit["entity"].get("prioritet"),
            "distance": hit["distance"],
        }
        for hit in result[0]
    ]