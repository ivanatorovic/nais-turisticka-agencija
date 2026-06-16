from fastapi import FastAPI

from controller.zalba_controller import router


app = FastAPI(
    title="Analiza Zalbi Service",
    description=(
        "Mikroservis za rad sa žalbama u Cassandra "
        "kolonskoj bazi podataka."
    ),
    version="1.0.0",
)

app.include_router(router)


@app.get(
    "/",
    tags=["Health"],
    summary="Provera rada servisa"
)
def root():
    return {
        "service": "analiza-zalbi",
        "status": "running",
        "docs": "/docs",
    }