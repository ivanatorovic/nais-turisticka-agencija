import json
import logging
from datetime import datetime, timezone

import pika

from messaging.rabbitmq_config import (
    CHOREOGRAPHY_EXCHANGE,
    COMPLAINT_CREATED_KEY,
    declare_rabbitmq_infrastructure,
    get_rabbitmq_connection,
)


logger = logging.getLogger(__name__)


def publish_complaint_created(
        saga_id: str,
        zalba_id: int,
        aktivnost_id: int,
        putnik_id: int
) -> None:
    connection = None

    try:
        connection = get_rabbitmq_connection()
        channel = connection.channel()

        declare_rabbitmq_infrastructure(channel)

        event = {
            "event_type": "COMPLAINT_CREATED",
            "saga_id": saga_id,
            "zalba_id": zalba_id,
            "aktivnost_id": aktivnost_id,
            "putnik_id": putnik_id,
            "timestamp": datetime.now(timezone.utc).isoformat()
        }

        channel.basic_publish(
            exchange=CHOREOGRAPHY_EXCHANGE,
            routing_key=COMPLAINT_CREATED_KEY,
            body=json.dumps(event),
            properties=pika.BasicProperties(
                content_type="application/json",
                delivery_mode=2
            )
        )

        logger.info(
            "Poslat COMPLAINT_CREATED: saga_id=%s, zalba_id=%s, aktivnost_id=%s",
            saga_id,
            zalba_id,
            aktivnost_id
        )

    finally:
        if connection is not None and connection.is_open:
            connection.close()