package com.example.sensorservice.rfsensor.sampledata;

import com.example.sensorservice.common.model.RFSensor;
import com.example.sensorservice.common.model.SensorStatus;

import java.util.List;

public class RFSensorSampleData {

    public static RFSensor correctActiveRFSensor(){
        return RFSensor.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(SensorStatus.ACTIVE)
                .maxFrequency(2.0)
                .minFrequency(1.0)
                .build();
    }

    public static RFSensor correctInactiveRFSensor(){
        return RFSensor.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(SensorStatus.INACTIVE)
                .maxFrequency(2.0)
                .minFrequency(1.0)
                .build();
    }

    public static RFSensor correctActiveRFSensor2(){
        return RFSensor.builder()
                .id("5ff893d79d260a2bebb6a82e")
                .ip("33.23.32.223")
                .sensorStatus(SensorStatus.ACTIVE)
                .maxFrequency(15.0)
                .minFrequency(2.0)
                .build();
    }

    public static List<RFSensor> rfSensors(){
        return List.of(correctActiveRFSensor(), correctActiveRFSensor2());
    }
}
