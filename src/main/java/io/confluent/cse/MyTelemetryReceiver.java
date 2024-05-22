package io.confluent.cse;

import org.apache.kafka.common.metrics.KafkaMetric;
import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.kafka.server.authorizer.AuthorizableRequestContext;
import org.apache.kafka.server.telemetry.ClientTelemetryPayload;
import org.apache.kafka.server.telemetry.ClientTelemetryReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MyTelemetryReceiver implements MetricsReporter {

    private static final Logger log = LoggerFactory.getLogger(MyTelemetryReceiver.class);

    @Override
    public void init(List<KafkaMetric> list) {
        log.info("*** MyTelemetryReceiver :: init() ***");
    }

    @Override
    public void metricChange(KafkaMetric kafkaMetric) {
        log.info("**** metricChange detected ****");
        log.info("** metricChange - name: "+kafkaMetric.metricName() + " - value: - " + kafkaMetric.metricValue().toString());
    }

    @Override
    public void metricRemoval(KafkaMetric kafkaMetric) {
        log.info("***** bye then! *****");
    }

    @Override
    public void close() {
        log.info("******************** CLOSE *************************");
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
