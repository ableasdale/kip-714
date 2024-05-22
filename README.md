# KIP-714

Testing [KIP-714: Client metrics and observability](https://cwiki.apache.org/confluence/display/KAFKA/KIP-714%3A+Client+metrics+and+observability)

## Prerequisites

This project requires the use of [Apache Kafka 3.7.0](https://kafka.apache.org/downloads) or later.

## Getting Started

The provided `docker-compose` file will initialise a single [Apache Kafka](https://hub.docker.com/r/apache/kafka) broker (running in KRaft mode).

The `broker` instance has been configured to use a pre-configured broker plugin for the metrics (`io.confluent.cse.KafkaClientTelemetry`).

This plugin needs to be provided to the Docker container before you can start the container up; if you start the container without creating the jar file containing the plugin, the broker will fail to start, logging a `org.apache.kafka.common.KafkaException: Class io.confluent.cse.KafkaClientTelemetry cannot be found` message.

To build the jar, run:

```bash
gradle shadowJar
```

To start the instance, run:

```bash
docker-compose up -d
```

Let's create a couple of test messages to get started:

```bash
gradle run
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


```bash
docker-compose exec broker bash
```

/opt/kafka/bin/kafka-client-metrics.sh --bootstrap-server $BROKERS --describe

### Notes

- Use KRaft
- Write or obtain a metrics reporter that implements `org.apache.kafka.server.telemetry.ClientTelemetry`. Put that class on the class path for the brokers.
- Change the broker configuration metric.reporters=<your class name>
- Start the brokers.
- Ensure that client telemetry APIs are supported using the kafka-broker-api-versions.sh tool
- Create a client metrics resource to start collecting metrics using the kafka-client-metrics.sh tool
- And then connect your clients (AK 3.7 or later)