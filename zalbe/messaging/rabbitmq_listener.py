import json
import logging
import time

import pika

from messaging.rabbitmq_config import (
    COMPLAINT_COUNTER_UPDATED_QUEUE,
    COMPLAINT_COUNTER_FAILED_QUEUE,
    declare_rabbitmq_infrastructure,
    get_rabbitmq_connection,
)
from repository.zalba_repository import update_saga_status


logger = logging.getLogger(__name__)


def obradi_uspesan_dogadjaj(event: dict) -> None:
    zalba_id = (
            event.get("complaintId")
            or event.get("complaint_id")
            or event.get("zalba_id")
    )

    if zalba_id is None:
        raise ValueError(
            f"Uspešan događaj ne sadrži ID žalbe: {event}"
        )

    rezultat = update_saga_status(
        int(zalba_id),
        "CONFIRMED"
    )

    if rezultat.get("status") != "success":
        raise RuntimeError(
            f"Nije uspela potvrda žalbe: {rezultat}"
        )

    logger.info(
        "Žalba ID=%s je potvrđena.",
        zalba_id
    )
def obradi_neuspesan_dogadjaj(event: dict) -> None:
    zalba_id = (
            event.get("complaintId")
            or event.get("complaint_id")
            or event.get("zalba_id")
    )

    if zalba_id is None:
        raise ValueError(
            f"Neuspešan događaj ne sadrži ID žalbe: {event}"
        )

    rezultat = update_saga_status(
        int(zalba_id),
        "FAILED"
    )

    if rezultat.get("status") != "success":
        raise RuntimeError(
            f"Nije uspelo odbijanje žalbe: {rezultat}"
        )

    logger.info(
        "Žalba ID=%s nije potvrđena. Razlog: %s",
        zalba_id,
        event.get("reason")
    )

def napravi_callback(handler):
    def callback(channel, method, properties, body):
        try:
            event = json.loads(body.decode("utf-8"))
            logger.info("PRIMLJEN RABBITMQ EVENT: %s", event)
            handler(event)

            channel.basic_ack(
                delivery_tag=method.delivery_tag
            )

        except Exception:
            logger.exception(
                "Greška tokom obrade RabbitMQ događaja."
            )

            channel.basic_nack(
                delivery_tag=method.delivery_tag,
                requeue=False,
            )

    return callback


def start_rabbitmq_listener() -> None:
    while True:
        connection = None

        try:
            connection = get_rabbitmq_connection()
            channel = connection.channel()

            declare_rabbitmq_infrastructure(channel)

            channel.basic_qos(prefetch_count=1)

            channel.basic_consume(
                queue=COMPLAINT_COUNTER_UPDATED_QUEUE,
                on_message_callback=napravi_callback(
                    obradi_uspesan_dogadjaj
                ),
                auto_ack=False,
            )

            channel.basic_consume(
                queue=COMPLAINT_COUNTER_FAILED_QUEUE,
                on_message_callback=napravi_callback(
                    obradi_neuspesan_dogadjaj
                ),
                auto_ack=False,
            )

            logger.info(
                "RabbitMQ listener je pokrenut. "
                "Čekam odgovore podsistema dodatnih aktivnosti."
            )

            channel.start_consuming()

        except pika.exceptions.AMQPError:
            logger.exception(
                "RabbitMQ nije dostupan. Ponovni pokušaj za 5 sekundi."
            )
            time.sleep(5)

        except Exception:
            logger.exception(
                "Neočekivana greška u RabbitMQ listener-u. "
                "Ponovni pokušaj za 5 sekundi."
            )
            time.sleep(5)

        finally:
            if connection is not None and connection.is_open:
                connection.close()