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

docker-compose exec kafka bash

kafka-configs.sh --bootstrap-server $BROKERS \
--entity-type client-metrics \
--entity-name "basic_producer_metrics" \
--alter \
--add-config "metrics=[org.apache.kafka.producer., org.apache.kafka.consumer.coordinator.rebalance.latency.max],interval.ms=15000,match=[client_instance_id=b69cc35a-7a54-4790-aa69-cc2bd4ee4538]"
bash: kafka-configs.sh: command not found

```bash
kafka:/$ /opt/kafka/bin/kafka-configs.sh --bootstrap-server kafka:9092    --entity-type client-metrics    --entity-name "basic_producer_metrics"    --alter    --add-config "metrics=[org.apache.kafka.producer., org.apache.kafka.consumer.coordinator.rebalance.latency.max],interval.ms=15000,match=[client_instance_id=b69cc35a-7a54-4790-aa69-cc2bd4ee4538]"
```

```bash
/opt/kafka/bin/kafka-configs.sh --bootstrap-server kafka:9092 --describe --entity-type client-metrics --entity-name "basic_producer_metrics"
```

```terminal
Dynamic configs for client-metric basic_producer_metrics are:
  interval.ms=15000 sensitive=false synonyms={}
  match=client_instance_id=b69cc35a-7a54-4790-aa69-cc2bd4ee4538 sensitive=false synonyms={}
  metrics=org.apache.kafka.producer., org.apache.kafka.consumer.coordinator.rebalance.latency.max sensitive=false synonyms={}
```

/opt/kafka/bin/kafka-client-metrics.sh --bootstrap-server kafka:9092 --describe --name "basic_producer_metrics"

```terminal
Client metrics configs for basic_producer_metrics are:
  interval.ms=15000
  match=client_instance_id=b69cc35a-7a54-4790-aa69-cc2bd4ee4538
  metrics=org.apache.kafka.producer., org.apache.kafka.consumer.coordinator.rebalance.latency.max
```



/opt/kafka/bin/kafka-client-metrics.sh --bootstrap-server $BROKERS --describe