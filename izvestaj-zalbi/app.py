
import logging

import uvicorn
from fastapi import FastAPI

from controller.izvestaj_controller import (
    router as izvestaj_router,
)


logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s %(levelname)-8s %(message)s"
)

logger = logging.getLogger(__name__)


app = FastAPI(
    title="Izvestaj Zalbi Service",
    description=(
        "Servis za formiranje Grafana izveštaja "
        "na osnovu podataka iz Cassandra i Milvus baze."
    ),
    version="1.1.0",
)


app.include_router(
    izvestaj_router
)


@app.get(
    "/",
    tags=["Health"]
)
def root():
    return {
        "service": "izvestaj-zalbi",
        "status": "running",
        "docs": "/docs",
        "izvestaj": "/izvestaj/pregled",
    }


@app.get(
    "/health",
    tags=["Health"]
)
def health():
    return {
        "status": "healthy"
    }


if __name__ == "__main__":
    logger.info(
        "Pokrećem Izvestaj Zalbi Service."
    )

    uvicorn.run(
        "app:app",
        host="0.0.0.0",
        port=8000,
        reload=True
    )

