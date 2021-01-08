package com.example.sensorservice.camerasensor.service;

import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.model.Camera;
import com.example.sensorservice.common.model.CameraDTO;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.common.model.SensorOperation;
import com.example.sensorservice.common.model.SensorStatus;
import com.example.sensorservice.common.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class CameraService implements SensorService {

    private CameraRepository cameraRepository;
    private CameraSenderService cameraSenderService;
    private CameraValidationService cameraValidationService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        cameraRepository.insert((Camera) sensor);
        cameraSenderService.send(new CameraDTO((Camera) sensor, SensorOperation.REGISTERED));
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        cameraValidationService.validateCameraSensorExists(id);
        Camera camera = cameraRepository.findById(id).get();
        cameraRepository.save(
                Camera.builder()
                        .sensorStatus(SensorStatus.INACTIVE)
                        .id(camera.getId())
                        .ip(camera.getIp())
                        .streamAddress(camera.getStreamAddress())
                        .panTiltZoom(camera.getPanTiltZoom())
                        .build());
        cameraSenderService.send(new CameraDTO(camera, SensorOperation.UNREGISTERED));
        return camera;
    }

    @Override
    public Sensor updateSensor(String id, Sensor sensor) {
        cameraValidationService.validateCameraSensorExists(id);
        Camera updatedCamera = cameraRepository.insert((Camera) sensor);
        cameraSenderService.send(new CameraDTO(updatedCamera, SensorOperation.UPDATED));
        return updatedCamera;
    }

    public List<Camera> getCameraSensors() {
        return cameraRepository.findAll();
    }
}
