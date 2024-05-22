package io.confluent.cse.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class TestJavaClient {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        final Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("client.id", "b69cc35a-7a54-4790-aa69-cc2bd4ee4538");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 500);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        props.put(ProducerConfig.ENABLE_METRICS_PUSH_CONFIG, true);
        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int j=0; j<50; j++) {
            for (int i = 0; i < 100000; i++) {
                produceTo(producer, "test-topic", "key" + i, "value" + i);
            }
            producer.flush();
        }
        producer.close();
    }

    private static void produceTo(Producer<String, String> producer, String topic, String key, String value) {
        producer.send(new ProducerRecord(topic, key, value),
                (event, ex) -> {
                    if (ex != null)
                        LOG.error("Exception:", ex);
                    else
                        LOG.debug(String.format("Produced event to topic : %s || key : %s || value : %s", event.topic(), key, value));
                });
    }
}
