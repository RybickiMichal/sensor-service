package com.example.sensorservice.camerasensor.sampledata;

import com.example.sensorservice.common.model.Camera;
import com.example.sensorservice.common.model.PanTiltZoom;
import com.example.sensorservice.common.model.SensorStatus;

import java.util.List;

public class CameraSampleData {

    public static Camera correctActiveCamera() {
        return Camera.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(SensorStatus.ACTIVE)
                .streamAddress("33.25.64.32")
                .panTiltZoom(new PanTiltZoom(2.0, 3.0, 4.0))
                .build();
    }

    public static Camera correctInactiveCamera() {
        return Camera.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(SensorStatus.INACTIVE)
                .streamAddress("33.25.64.32")
                .panTiltZoom(new PanTiltZoom(2.0, 3.0, 4.0))
                .build();
    }

    public static Camera correctActiveCamera2() {
        return Camera.builder()
                .id("5ff893d79d260a2bebb6a82e")
                .ip("33.23.32.223")
                .sensorStatus(SensorStatus.ACTIVE)
                .streamAddress("134.52.233.55")
                .panTiltZoom(new PanTiltZoom(55.0, 77.0, 88.0))
                .build();
    }

    public static List<Camera> cameraSensors() {
        return List.of(correctActiveCamera(), correctActiveCamera2());
    }
}
