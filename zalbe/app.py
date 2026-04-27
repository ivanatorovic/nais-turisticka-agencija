"""
Glavna FastAPI aplikacija za VectorDatabaseService (Zalbe).
"""
import logging
import uvicorn
from fastapi import FastAPI

from config import APP_HOST, APP_PORT, APP_NAME
from controller.zalba_controller import router as zalba_router
from controller.ocena_controller import router as ocena_router

# Logger
logging.basicConfig(level=logging.INFO, format="%(asctime)s  %(levelname)-8s %(message)s")
logger = logging.getLogger(__name__)

# Kreiraj FastAPI aplikaciju
app = FastAPI(
    title="VectorDatabaseService - Zalbe",
    description="Mikroservis za upravljanje žalbama i ocenama putnika turističke agencije (Milvus vektorska baza)",
    version="1.0.0",
)

# Registruj router-e
app.include_router(zalba_router)
app.include_router(ocena_router)


@app.get("/", tags=["Health"])
def root():
    """Health check endpoint."""
    return {
        "service": APP_NAME,
        "status": "running",
        "docs": "/docs"
    }


if __name__ == "__main__":
    logger.info("Pokrećem %s na %s:%d", APP_NAME, APP_HOST, APP_PORT)
    uvicorn.run("app:app", host=APP_HOST, port=APP_PORT, reload=True)