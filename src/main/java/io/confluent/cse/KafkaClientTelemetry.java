package io.confluent.cse;

import io.opentelemetry.proto.metrics.v1.MetricsData;
import org.apache.kafka.common.metrics.KafkaMetric;
import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.kafka.server.authorizer.AuthorizableRequestContext;
import org.apache.kafka.server.telemetry.ClientTelemetry;
import org.apache.kafka.server.telemetry.ClientTelemetryPayload;
import org.apache.kafka.server.telemetry.ClientTelemetryReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KafkaClientTelemetry implements ClientTelemetry, MetricsReporter, ClientTelemetryReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init(List<KafkaMetric> metrics) {
        LOG.info("*** KafkaClientTelemetry :: init() ***");
        for (KafkaMetric m : metrics){
            LOG.info("Metric Name: "+m.metricName()+" - value: -  "+m.metricValue().toString());
        }
    }

    @Override
    public void metricChange(KafkaMetric kafkaMetric) {
        LOG.info("**** metricChange detected ****");
        LOG.info("** metricChange - name: "+kafkaMetric.metricName() + " - value: - " + kafkaMetric.metricValue().toString());
    }

    @Override
    public void metricRemoval(KafkaMetric metric) {
        LOG.info("**** metricRemoval detected ****");
    }

    @Override
    public void close() {
        LOG.info("******************** CLOSE *************************");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        LOG.info("*** KafkaClientTelemetry :: configure() ***");
        // TODO - log out the config here?
    }

    @Override
    public ClientTelemetryReceiver clientReceiver() {
        return this;
    }

    @Override
    public void exportMetrics(AuthorizableRequestContext context, ClientTelemetryPayload payload) {
        LOG.info("***** exportMetrics *****");
        try {
            MetricsData data = MetricsData.parseFrom(payload.data());

            LOG.info("+++ CLIENT TELEMETRY: clientInstanceId=" + payload.clientInstanceId()
                    + ", isTerminating=" + payload.isTerminating()
                    + ", contentType=" + payload.contentType()
                    + ", metrics="
                    + data.getResourceMetricsList()
                    .stream()
                    .map(rm -> rm.getScopeMetricsList().get(0).getMetrics(0).getName())
                    .collect(Collectors.joining(",", "[", "]")));
        } catch (Exception e) {
            LOG.info("+++ CLIENT TELEMETRY: clientInstanceId=" + payload.clientInstanceId()
                    + ", isTerminating=" + payload.isTerminating()
                    + ", contentType=" + payload.contentType()
                    + ", exception=" + e);
        }
    }
}