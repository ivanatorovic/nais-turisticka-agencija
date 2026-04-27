
import os

# ============================================================
# MILVUS KONEKCIJA
# ============================================================
MILVUS_HOST = os.getenv("MILVUS_HOST", "localhost")
MILVUS_PORT = int(os.getenv("MILVUS_PORT", "19530"))
MILVUS_URI = f"http://{MILVUS_HOST}:{MILVUS_PORT}"

# ============================================================
# EMBEDDING MODEL (za generisanje vektora iz teksta)
# ============================================================
# Sentence-transformers model: MiniLM (384 dimenzije, brz, dovoljno dobar za srpski/engleski)
EMBEDDING_MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2"
EMBEDDING_DIM = 384

# ============================================================
# KOLEKCIJE
# ============================================================
ZALBE_COLLECTION = "zalbe"
OCENE_COLLECTION = "ocene_resavanja"

# ============================================================
# INDEKSIRANJE I PRETRAGA
# ============================================================
# IVF_FLAT parametri - kompromis brzina/preciznost
NLIST = 64      # broj klastera pri indeksiranju
NPROBE = 16     # broj klastera koji se pretražuju pri upitu

# Default top-K za upite (koliko rezultata vraćamo)
DEFAULT_TOP_K = 5

# ============================================================
# UNOS PODATAKA
# ============================================================
BATCH_SIZE = 64  # koliko slogova odjednom šaljemo u Milvus

# ============================================================
# FASTAPI / UVICORN
# ============================================================
APP_HOST = "0.0.0.0"
APP_PORT = int(os.getenv("APP_PORT", "8000"))
APP_NAME = "zalbe-service"

# ============================================================
# OLLAMA (opciono, ako budemo koristili umesto sentence-transformers)
# ============================================================
OLLAMA_URL = os.getenv("OLLAMA_URL", "http://localhost:11434")
OLLAMA_MODEL = os.getenv("OLLAMA_MODEL", "nomic-embed-text")