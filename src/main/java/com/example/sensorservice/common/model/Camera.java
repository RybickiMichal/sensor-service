package com.example.sensorservice.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Document
@Getter
@ToString(callSuper = true)
public class Camera extends Sensor {

    @NotBlank
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    private String streamAddress;

    @NotNull
    private PanTiltZoom panTiltZoom;

    @Builder
    public Camera(String id, String ip, SensorStatus sensorStatus, String streamAddress, PanTiltZoom panTiltZoom) {
        super(id, sensorStatus, ip);
        this.streamAddress = streamAddress;
        this.panTiltZoom = panTiltZoom;
    }
}
