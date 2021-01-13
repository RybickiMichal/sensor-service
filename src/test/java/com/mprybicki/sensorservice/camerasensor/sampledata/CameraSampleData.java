package com.mprybicki.sensorservice.camerasensor.sampledata;

import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.PanTiltZoom;

import java.util.List;

import static com.mprybicki.sensorservice.common.model.SensorStatus.ACTIVE;
import static com.mprybicki.sensorservice.common.model.SensorStatus.INACTIVE;

public class CameraSampleData {

    public static Camera correctActiveCamera() {
        return Camera.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(ACTIVE)
                .streamAddress("33.25.64.32")
                .panTiltZoom(new PanTiltZoom(2.0, 3.0, 4.0))
                .build();
    }

    public static Camera correctInactiveCamera() {
        return Camera.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(INACTIVE)
                .streamAddress("33.25.64.32")
                .panTiltZoom(new PanTiltZoom(2.0, 3.0, 4.0))
                .build();
    }

    public static Camera correctActiveCamera2() {
        return Camera.builder()
                .id("5ff893d79d260a2bebb6a82e")
                .ip("33.23.32.223")
                .sensorStatus(ACTIVE)
                .streamAddress("134.52.233.55")
                .panTiltZoom(new PanTiltZoom(55.0, 77.0, 88.0))
                .build();
    }

    public static Camera correctActiveCameraRegisteredToCameraSensor() {
        Camera camera = Camera.builder()
                .id("5ff8832b9d260a2bebb6a82d")
                .ip("22.222.2.22")
                .sensorStatus(ACTIVE)
                .streamAddress("33.25.64.32")
                .panTiltZoom(new PanTiltZoom(2.0, 3.0, 4.0))
                .build();
        camera.setCameraServicePort(8000);
        return camera;
    }

    public static List<Camera> cameraSensors() {
        return List.of(correctActiveCamera(), correctActiveCamera2());
    }
}
