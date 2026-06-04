import os

CASSANDRA_HOST = os.getenv("CASSANDRA_HOST", "cassandradb")
CASSANDRA_PORT = int(os.getenv("CASSANDRA_PORT", "9042"))
CASSANDRA_KEYSPACE = os.getenv("CASSANDRA_KEYSPACE", "zalbe_keyspace")

REDIS_HOST = os.getenv("REDIS_HOST", "redisdb")
REDIS_PORT = int(os.getenv("REDIS_PORT", "6379"))

APP_HOST = "0.0.0.0"
APP_PORT = int(os.getenv("APP_PORT", "8000"))
APP_NAME = "analiza-zalbi-service"