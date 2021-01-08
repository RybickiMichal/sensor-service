package com.example.sensorservice.rfsensor.service;

import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.RFSensor;
import com.example.sensorservice.common.model.RFSensorDTO;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.common.model.SensorOperation;
import com.example.sensorservice.common.model.SensorStatus;
import com.example.sensorservice.common.service.SensorService;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RFSensorService implements SensorService {

    private RFSensorRepository rfSensorRepository;
    private RFSensorSenderService rfSensorSenderService;
    private RFSensorValidationService rfSensorValidationService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        rfSensorRepository.insert((RFSensor) sensor);
        rfSensorSenderService.send(new RFSensorDTO((RFSensor) sensor, SensorOperation.REGISTERED));
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        RFSensor rfSensor = rfSensorRepository.findById(id).orElseThrow(() -> new InvalidSensorException("RF Sensor with given id does't exist"));
        RFSensor inactiveRFSensor = RFSensor.builder()
                .sensorStatus(SensorStatus.INACTIVE)
                .id(rfSensor.getId())
                .ip(rfSensor.getIp())
                .maxFrequency(rfSensor.getMaxFrequency())
                .minFrequency(rfSensor.getMinFrequency())
                .build();
        rfSensorRepository.save(inactiveRFSensor);
        rfSensorSenderService.send(new RFSensorDTO(rfSensor, SensorOperation.UNREGISTERED));
        return inactiveRFSensor;
    }

    @Override
    public Sensor updateSensor(String id, Sensor sensor) {
        rfSensorValidationService.validateIsRFSensorExists(id);
        RFSensor rfSensor = rfSensorRepository.save((RFSensor) sensor);
        rfSensorSenderService.send(new RFSensorDTO(rfSensor, SensorOperation.UPDATED));
        return rfSensor;
    }

    public List<RFSensor> getRFSensors() {
        return rfSensorRepository.findAll();
    }
}
