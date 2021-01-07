package com.example.sensorservice.controller;

import com.example.sensorservice.model.Camera;
import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.service.CameraService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class CameraController {

    private CameraService cameraService;

    @PostMapping(value = "/camera/register")
    public Sensor registerCameraSensor(@RequestBody Camera camera) {
        return cameraService.registerSensor(camera);
    }

    @DeleteMapping(value = "/camera/unregister/{id}")
    public void unregisterCameraSensor(@PathVariable String id) {
        cameraService.unregisterSensor(id);
    }

    @PutMapping(value = "/camera/update/{id}")
    public Sensor updateCameraSensor(@PathVariable String id, @RequestBody Camera camera) {
        return cameraService.updateSensor(id, camera);
    }

    @GetMapping(value = "/camera")
    public List<Camera> getCameraSensors() {
        return cameraService.getCameraSensors();
    }

}
