package com.mprybicki.sensorservice.rfsensor.sampledata;

import com.mprybicki.sensorservice.common.model.RFSensor;

import java.util.List;

import static com.mprybicki.sensorservice.common.model.SensorStatus.ACTIVE;
import static com.mprybicki.sensorservice.common.model.SensorStatus.INACTIVE;

public class RFSensorSampleData {

    public static RFSensor correctActiveRFSensor() {
        return RFSensor.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(ACTIVE)
                .maxFrequency(2.0)
                .minFrequency(1.0)
                .build();
    }

    public static RFSensor correctInactiveRFSensor() {
        return RFSensor.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("23.232.2.22")
                .sensorStatus(INACTIVE)
                .maxFrequency(2.0)
                .minFrequency(1.0)
                .build();
    }

    public static RFSensor correctActiveRFSensor2() {
        return RFSensor.builder()
                .id("5ff893d79d260a2bebb6a82e")
                .ip("33.23.32.223")
                .sensorStatus(ACTIVE)
                .maxFrequency(15.0)
                .minFrequency(2.0)
                .build();
    }

    public static List<RFSensor> rfSensors() {
        return List.of(correctActiveRFSensor(), correctActiveRFSensor2());
    }
}
