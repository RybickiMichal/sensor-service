package com.example.sensorservice.service;

import com.example.sensorservice.model.Camera;
import com.example.sensorservice.model.CameraDTO;
import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.model.SensorOperation;
import com.example.sensorservice.repository.CameraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class CameraService implements SensorService {

    private CameraRepository cameraRepository;
    private CameraSenderService cameraSenderService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        //TODO validate sensor
        cameraRepository.insert((Camera) sensor);
        cameraSenderService.send(new CameraDTO((Camera) sensor, SensorOperation.REGISTERED));
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        //TODO validate isSensorExists
        Camera camera = cameraRepository.findById(id).get();
        cameraRepository.deleteById(id);
        cameraSenderService.send(new CameraDTO(camera, SensorOperation.UNREGISTERED));
        return camera;
    }

    @Override
    public Sensor updateSensor(String id, Sensor sensor) {
        //TODO validate sensor
        Camera updatedCamera = cameraRepository.insert((Camera) sensor);
        cameraSenderService.send(new CameraDTO(updatedCamera, SensorOperation.UPDATED));
        return updatedCamera;
    }

    public List<Camera> getCameraSensors() {
        return cameraRepository.findAll();
    }
}
