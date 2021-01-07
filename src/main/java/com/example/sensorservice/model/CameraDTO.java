package com.example.sensorservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Getter
@ToString
public class CameraDTO {
    Camera camera;
    @Field
    SensorOperation sensorOperation;
}
