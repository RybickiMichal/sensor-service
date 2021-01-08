package com.example.sensorservice.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rfSensor")
@Getter
@ToString(callSuper = true)
public class RFSensor extends Sensor {
    private Double minFrequency;
    private Double maxFrequency;

    @Builder
    public RFSensor(String id, String ip, SensorStatus sensorStatus, Double minFrequency, Double maxFrequency) {
        super(id, sensorStatus, ip);
        this.minFrequency = minFrequency;
        this.maxFrequency = maxFrequency;
    }
}
