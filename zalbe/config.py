
import os


MILVUS_HOST = os.getenv("MILVUS_HOST", "localhost")
MILVUS_PORT = int(os.getenv("MILVUS_PORT", "19530"))
MILVUS_URI = f"http://{MILVUS_HOST}:{MILVUS_PORT}"


EMBEDDING_MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2"
EMBEDDING_DIM = 384


ZALBE_COLLECTION = "zalbe"
OCENE_COLLECTION = "ocene_resavanja"


NLIST = 64     
NPROBE = 16    


DEFAULT_TOP_K = 5


BATCH_SIZE = 64  


APP_HOST = "0.0.0.0"
APP_PORT = int(os.getenv("APP_PORT", "8000"))
APP_NAME = "zalbe-service"


OLLAMA_URL = os.getenv("OLLAMA_URL", "http://localhost:11434")
OLLAMA_MODEL = os.getenv("OLLAMA_MODEL", "nomic-embed-text")

CASSANDRA_HOST = os.getenv("CASSANDRA_HOST", "cassandra")
CASSANDRA_PORT = int(os.getenv("CASSANDRA_PORT", "9042"))
CASSANDRA_KEYSPACE = os.getenv("CASSANDRA_KEYSPACE", "zalbe_keyspace")