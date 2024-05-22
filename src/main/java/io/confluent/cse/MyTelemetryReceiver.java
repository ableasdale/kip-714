package io.confluent.cse;

import org.apache.kafka.common.metrics.KafkaMetric;
import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.kafka.server.authorizer.AuthorizableRequestContext;
import org.apache.kafka.server.telemetry.ClientTelemetryPayload;
import org.apache.kafka.server.telemetry.ClientTelemetryReceiver;

import java.util.List;
import java.util.Map;

public class MyTelemetryReceiver implements MetricsReporter {

    @Override
    public void init(List<KafkaMetric> list) {
        System.out.println("***** DOING SOME STUFF!! *******");
        System.out.println("***** HEY LOOK, I AM DOING SOME STUFF!! *******");
    }

    @Override
    public void metricChange(KafkaMetric kafkaMetric) {

    }

    @Override
    public void metricRemoval(KafkaMetric kafkaMetric) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
