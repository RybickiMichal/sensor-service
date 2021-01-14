package com.mprybicki.sensorservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public abstract class Sensor {
    @Id
    private String id;

    @Field
    @NotNull
    private SensorStatus sensorStatus;

    @NotBlank
    private String ip;

}
