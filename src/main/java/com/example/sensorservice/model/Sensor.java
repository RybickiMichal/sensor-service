package com.example.sensorservice.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public abstract class Sensor {
    @Id
    private String id;
    @Field
    private SensorStatus sensorStatus;

    private String ip;

    public Sensor(String ip, SensorStatus sensorStatus) {
        this.ip = ip;
        this.sensorStatus = sensorStatus;
    }
}
