package com.example.sensorservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Getter
@ToString
public abstract class Sensor {
    @Id
    private String id;
    @Field
    private SensorStatus sensorStatus;

    private String ip;

}
