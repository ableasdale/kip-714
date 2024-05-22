package io.confluent.cse;

import org.apache.kafka.server.telemetry.ClientTelemetry;
import org.apache.kafka.server.telemetry.ClientTelemetryReceiver;

public class TelemetryReporter implements ClientTelemetry {
    @Override
    public ClientTelemetryReceiver clientReceiver() {
        System.out.println("*********** HERE ***********");
        //this.log.debug
        return null;
    }
}