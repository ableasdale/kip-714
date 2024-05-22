package io.confluent.cse.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
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
        Producer<String, String> producer = new KafkaProducer<>(props);
        produceTo(producer, "test-topic", "key1", "value1");
        produceTo(producer, "test-topic", "key2", "value2");
        producer.flush();
        producer.close();
    }

    private static void produceTo(Producer<String, String> producer, String topic, String key, String value) {
        producer.send(new ProducerRecord(topic, key, value),
                (event, ex) -> {
                    if (ex != null)
                        LOG.error("Exception:", ex);
                    else
                        LOG.info(String.format("Produced event to topic : %s || key : %s || value : %s", event.topic(), key, value));
                });
    }
}
