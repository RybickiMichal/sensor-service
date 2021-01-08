package com.example.sensorservice.rfsensor.service;

import com.example.sensorservice.common.model.RFSensor;
import com.example.sensorservice.common.model.RFSensorDTO;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.common.model.SensorOperation;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import com.example.sensorservice.common.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RFSensorService implements SensorService {

    private RFSensorRepository rfSensorRepository;
    private RFSensorSenderService rfSensorSenderService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        //TODO validate sensor
        rfSensorRepository.insert((RFSensor) sensor);
        rfSensorSenderService.send(new RFSensorDTO((RFSensor) sensor, SensorOperation.REGISTERED));
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        //TODO validate isSensorExists
        RFSensor rfSensor = rfSensorRepository.findById(id).get();
        rfSensorRepository.deleteById(id);
        rfSensorSenderService.send(new RFSensorDTO(rfSensor, SensorOperation.UNREGISTERED));
        return rfSensor;
    }

    @Override
    public Sensor updateSensor(String id, Sensor sensor) {
        //TODO validate sensor
        RFSensor rfSensor = rfSensorRepository.insert((RFSensor) sensor);
        rfSensorSenderService.send(new RFSensorDTO(rfSensor, SensorOperation.UPDATED));
        return rfSensor;
    }

    public List<RFSensor> getRFSensors() {
        return rfSensorRepository.findAll();
    }
}
