package com.mprybicki.sensorservice.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Document(collection = "rfSensor")
@Getter
@ToString(callSuper = true)
public class RFSensor extends Sensor {

    @Min(0)
    @NotNull
    private Double minFrequency;

    @Min(0)
    @NotNull
    private Double maxFrequency;

    @Builder
    public RFSensor(String id, String ip, SensorStatus sensorStatus, Double minFrequency, Double maxFrequency) {
        super(id, sensorStatus, ip);
        this.minFrequency = minFrequency;
        this.maxFrequency = maxFrequency;
    }
}
