
import logging
from sentence_transformers import SentenceTransformer
from config import EMBEDDING_MODEL_NAME, EMBEDDING_DIM

logger = logging.getLogger(__name__)


class EmbeddingService:
    """Singleton servis za generisanje embeddinga."""

    def __init__(self):
        logger.info("Učitavanje embedding modela: %s", EMBEDDING_MODEL_NAME)
        self.model = SentenceTransformer(EMBEDDING_MODEL_NAME)
        logger.info("Model učitan (dimenzija: %d).", EMBEDDING_DIM)

    def encode(self, texts: list[str]) -> list[list[float]]:
        """
        Pretvara listu stringova u listu vektora.
        Svaki vektor ima dimenziju EMBEDDING_DIM (384).
        """
        if not texts:
            return []
        embeddings = self.model.encode(
            texts,
            normalize_embeddings=True,  # L2 normalizacija za cosine sličnost
            show_progress_bar=False
        )
        # Vraćamo kao običnu Python listu (Milvus ne voli numpy nizove)
        return embeddings.tolist()

    def encode_one(self, text: str) -> list[float]:
        """Pretvara jedan string u jedan vektor."""
        return self.encode([text])[0]


# Singleton instanca
embedding_service = EmbeddingService()