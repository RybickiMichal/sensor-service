package com.example.sensorservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@ToString(callSuper = true)
public class Camera extends Sensor {
    private String streamAddress;
    private PanTiltZoom panTiltZoom;

    @Builder
    public Camera(String ip, SensorStatus sensorStatus, String streamAddress, PanTiltZoom panTiltZoom) {
        super(ip, sensorStatus);
        this.streamAddress = streamAddress;
        this.panTiltZoom = panTiltZoom;
    }
}
