package com.example.sensorservice.service;

import com.example.sensorservice.model.Camera;
import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.repository.CameraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class CameraService implements SensorService {

    private CameraRepository cameraRepository;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        //TODO validate sensor
        return cameraRepository.insert((Camera) sensor);
    }

    @Override
    public void unregisterSensor(String id) {
        //TODO validate isSensorExists
        cameraRepository.deleteById(id);
    }

    @Override
    public Sensor updateSensor(String id, Sensor sensor) {
        //TODO validate sensor
        return cameraRepository.insert((Camera) sensor);
    }

    public List<Camera> getCameraSensors() {
        return cameraRepository.findAll();
    }
}
