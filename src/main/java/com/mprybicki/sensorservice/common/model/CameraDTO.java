package com.mprybicki.sensorservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public class CameraDTO {

    @NotNull
    Camera camera;

    @Field
    @NotNull
    SensorOperation sensorOperation;
}
