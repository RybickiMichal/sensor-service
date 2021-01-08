package com.example.sensorservice.common.model;

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
    public Camera(String id, String ip, SensorStatus sensorStatus, String streamAddress, PanTiltZoom panTiltZoom) {
        super(id, sensorStatus, ip);
        this.streamAddress = streamAddress;
        this.panTiltZoom = panTiltZoom;
    }
}
