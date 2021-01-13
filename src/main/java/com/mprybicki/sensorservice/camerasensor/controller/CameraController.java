package com.mprybicki.sensorservice.camerasensor.controller;

import com.mprybicki.sensorservice.camerasensor.service.CameraService;
import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.Sensor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class CameraController {

    private CameraService cameraService;

    @PostMapping(value = "/camera/register")
    public Sensor registerCameraSensor(@Valid @RequestBody Camera camera) {
        return cameraService.registerSensor(camera);
    }

    @DeleteMapping(value = "/camera/unregister/{id}")
    public Sensor unregisterCameraSensor(@PathVariable String id) {
        return cameraService.unregisterSensor(id);
    }

    @PutMapping(value = "/camera/update/{id}")
    public Sensor updateCameraSensor(@PathVariable String id, @Valid @RequestBody Camera camera) {
        return cameraService.updateSensor(id, camera);
    }

    @GetMapping(value = "/camera-sensors/active")
    public List<Camera> getActiveCameraSensors() {
        return cameraService.getActiveCameraSensors();
    }

    @GetMapping(value = "/camera/register-service/{cameraSensorId}/{cameraServicePort}")
    public Camera registerCameraServiceToCameraSensor(@PathVariable String cameraSensorId, @PathVariable int cameraServicePort) {
        return cameraService.registerCameraServiceToCameraSensor(cameraSensorId, cameraServicePort);
    }

    @GetMapping(value = "/camera/not-registered-to-camera-service")
    public Optional<Camera> getFirstCameraSensorWithoutRegisteredCameraService() {
        return cameraService.getFirstCameraSensorWithoutRegisteredCameraService();
    }
}
