package com.mprybicki.sensorservice.rfsensor.service;

import com.mprybicki.sensorservice.common.model.RFSensor;
import com.mprybicki.sensorservice.common.model.RFSensorDTO;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.common.model.SensorOperation;
import com.mprybicki.sensorservice.common.model.SensorStatus;
import com.mprybicki.sensorservice.common.service.SensorService;
import com.mprybicki.sensorservice.rfsensor.repository.RFSensorRepository;
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
        rfSensorValidationService.validateUnregisterSensor(id);
        RFSensor rfSensor = rfSensorRepository.findById(id).get();
        RFSensor inactiveRFSensor = RFSensor.builder()
                .sensorStatus(SensorStatus.INACTIVE)
                .id(rfSensor.getId())
                .ip(rfSensor.getIp())
                .maxFrequency(rfSensor.getMaxFrequency())
                .minFrequency(rfSensor.getMinFrequency())
                .build();
        rfSensorRepository.save(inactiveRFSensor);
        rfSensorSenderService.send(new RFSensorDTO(inactiveRFSensor, SensorOperation.UNREGISTERED));
        return inactiveRFSensor;
    }

    @Override
    public Sensor updateSensor(String id, Sensor newSensor) {
        rfSensorValidationService.validateUpdateSensor(id, newSensor);
        rfSensorRepository.save((RFSensor) newSensor);
        rfSensorSenderService.send(new RFSensorDTO((RFSensor) newSensor, SensorOperation.UPDATED));
        return newSensor;
    }

    public List<RFSensor> getRFSensors() {
        return rfSensorRepository.findAll();
    }
}
