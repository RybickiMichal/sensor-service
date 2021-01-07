package com.example.sensorservice.service;

import com.example.sensorservice.model.Camera;
import com.example.sensorservice.model.RFSensor;
import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RFSensorService implements SensorService {

    private RFSensorRepository rfSensorRepository;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        //TODO validate sensor
        return rfSensorRepository.insert((RFSensor) sensor);
    }

    @Override
    public void unregisterSensor(String id) {
        //TODO validate isSensorExists
        rfSensorRepository.deleteById(id);
    }

    @Override
    public Sensor updateSensor(String id, Sensor sensor) {
        //TODO validate sensor
        return rfSensorRepository.insert((RFSensor) sensor);
    }

    public List<RFSensor> getRFSensors() {
        return rfSensorRepository.findAll();
    }
}
