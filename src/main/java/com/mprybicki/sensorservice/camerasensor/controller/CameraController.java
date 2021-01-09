package com.mprybicki.sensorservice.camerasensor.controller;

import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.camerasensor.service.CameraService;
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

    @GetMapping(value = "/camera-sensors")
    public List<Camera> getCameraSensors() {
        return cameraService.getCameraSensors();
    }

}
