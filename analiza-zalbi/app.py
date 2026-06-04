from fastapi import FastAPI
from controller.zalba_controller import router

app = FastAPI(
    title="Analiza Zalbi Service"
)

app.include_router(router)