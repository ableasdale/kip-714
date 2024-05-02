# kip-714
Testing KIP-714 (https://cwiki.apache.org/confluence/display/KAFKA/KIP-714%3A+Client+metrics+and+observability)

## Getting Started

```bash
docker-compose up -d
```

### Initial testing

```bash
docker-compose exec kafka bash
```

Or:

```bash
docker exec -ti kafka /bin/bash

****
./opt/kafka/bin/kafka-topics.sh
docker exec -ti kafka /opt/kafka/bin/kafka-topics.sh
```

Workings

```bash
docker-compose exec kafka /opt/kafka/bin/kafka-topics.sh --describe

docker-compose exec kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server kafka:9092 --describe

docker-compose exec kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server kafka:9092 --create

docker-compose exec kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server kafka:9092 --create --topic test-topic
Created topic test-topic.

docker-compose exec kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server kafka:9092 --list
```

Let's produce:

```bash
docker-compose exec kafka /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server kafka:9092 --topic test-topic
```
