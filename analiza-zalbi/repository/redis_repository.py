import os
import json
import redis

REDIS_HOST = os.getenv("REDIS_HOST", "redis-db")
REDIS_PORT = int(os.getenv("REDIS_PORT", "6379"))

redis_client = redis.Redis(
    host=REDIS_HOST,
    port=REDIS_PORT,
    decode_responses=True
)


def save_zalba_to_cache(zalba_id: int, zalba_data: dict):
    key = f"zalba:{zalba_id}"
    redis_client.set(key, json.dumps(zalba_data))


def get_zalba_from_cache(zalba_id: int):
    key = f"zalba:{zalba_id}"
    data = redis_client.get(key)

    if data is None:
        return None

    return json.loads(data)


def delete_zalba_from_cache(zalba_id: int):
    key = f"zalba:{zalba_id}"
    redis_client.delete(key)


def clear_zalbe_cache():
    for key in redis_client.scan_iter("zalba:*"):
        redis_client.delete(key)