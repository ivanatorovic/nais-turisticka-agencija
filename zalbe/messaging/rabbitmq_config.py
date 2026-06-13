import os

import pika


RABBITMQ_HOST = os.getenv("RABBITMQ_HOST", "rabbitmq")
RABBITMQ_PORT = int(os.getenv("RABBITMQ_PORT", "5672"))
RABBITMQ_USER = os.getenv("RABBITMQ_USER", "guest")
RABBITMQ_PASSWORD = os.getenv("RABBITMQ_PASSWORD", "guest")

CHOREOGRAPHY_EXCHANGE = "saga.choreography.exchange"

COMPLAINT_CREATED_QUEUE = "complaint.created.queue"
COMPLAINT_CREATED_KEY = "complaint.created"

COMPLAINT_COUNTER_UPDATED_QUEUE = "complaint.counter.updated.queue"
COMPLAINT_COUNTER_UPDATED_KEY = "complaint.counter.updated"

COMPLAINT_COUNTER_FAILED_QUEUE = "complaint.counter.failed.queue"
COMPLAINT_COUNTER_FAILED_KEY = "complaint.counter.failed"


def get_rabbitmq_connection() -> pika.BlockingConnection:
    credentials = pika.PlainCredentials(
        username=RABBITMQ_USER,
        password=RABBITMQ_PASSWORD
    )

    parameters = pika.ConnectionParameters(
        host=RABBITMQ_HOST,
        port=RABBITMQ_PORT,
        credentials=credentials,
        heartbeat=60,
        blocked_connection_timeout=30
    )

    return pika.BlockingConnection(parameters)


def declare_rabbitmq_infrastructure(channel) -> None:
    channel.exchange_declare(
        exchange=CHOREOGRAPHY_EXCHANGE,
        exchange_type="topic",
        durable=True
    )

    channel.queue_declare(
        queue=COMPLAINT_CREATED_QUEUE,
        durable=True
    )

    channel.queue_declare(
        queue=COMPLAINT_COUNTER_UPDATED_QUEUE,
        durable=True
    )

    channel.queue_declare(
        queue=COMPLAINT_COUNTER_FAILED_QUEUE,
        durable=True
    )

    channel.queue_bind(
        exchange=CHOREOGRAPHY_EXCHANGE,
        queue=COMPLAINT_CREATED_QUEUE,
        routing_key=COMPLAINT_CREATED_KEY
    )

    channel.queue_bind(
        exchange=CHOREOGRAPHY_EXCHANGE,
        queue=COMPLAINT_COUNTER_UPDATED_QUEUE,
        routing_key=COMPLAINT_COUNTER_UPDATED_KEY
    )

    channel.queue_bind(
        exchange=CHOREOGRAPHY_EXCHANGE,
        queue=COMPLAINT_COUNTER_FAILED_QUEUE,
        routing_key=COMPLAINT_COUNTER_FAILED_KEY
    )