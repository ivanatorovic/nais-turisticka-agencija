
import logging
from pymilvus import MilvusClient
from config import MILVUS_URI

logger = logging.getLogger(__name__)


class MilvusService:
    """Singleton servis za rad sa Milvus bazom."""

    def __init__(self):
        logger.info("Povezivanje na Milvus: %s", MILVUS_URI)
        self.client = MilvusClient(uri=MILVUS_URI)
        logger.info("Uspešno povezano na Milvus.")

    def has_collection(self, collection_name: str) -> bool:
        """Proverava da li kolekcija postoji."""
        return self.client.has_collection(collection_name)

    def create_collection(self, collection_name: str, schema, index_params):
        """Kreira novu kolekciju sa datom šemom i indeksima."""
        self.client.create_collection(
            collection_name=collection_name,
            schema=schema,
            index_params=index_params,
            consistency_level="Strong",
        )
        logger.info("Kreirana kolekcija '%s'.", collection_name)

    def drop_collection(self, collection_name: str):
        """Briše kolekciju."""
        if self.has_collection(collection_name):
            self.client.drop_collection(collection_name)
            logger.info("Obrisana kolekcija '%s'.", collection_name)

    def load_collection(self, collection_name: str):
        """Učitava kolekciju u memoriju (obavezno pre pretrage)."""
        self.client.load_collection(collection_name)
        logger.info("Učitana kolekcija '%s' u memoriju.", collection_name)

    def insert(self, collection_name: str, data: list[dict]):
        """Unosi listu slogova u kolekciju."""
        result = self.client.insert(collection_name=collection_name, data=data)
        return result

    def count(self, collection_name: str) -> int:
        """Vraća broj slogova u kolekciji."""
        stats = self.client.get_collection_stats(collection_name)
        return int(stats.get("row_count", 0))


# Singleton instanca - koristi se kroz ceo projekat
milvus_service = MilvusService()