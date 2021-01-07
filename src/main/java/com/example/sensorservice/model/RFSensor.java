package com.example.sensorservice.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class RFSensor extends Sensor {
    private Double minFrequency;
    private Double maxFrequency;

    public RFSensor(String ip, SensorStatus status, Double minFrequency, Double maxFrequency) {
        super(ip, status);
        this.minFrequency = minFrequency;
        this.maxFrequency = maxFrequency;
    }
}
