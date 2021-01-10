package com.mprybicki.sensorservice.rfsensor.service;

import com.mprybicki.sensorservice.common.model.RFSensor;
import com.mprybicki.sensorservice.common.model.RFSensorDTO;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.common.service.SensorService;
import com.mprybicki.sensorservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mprybicki.sensorservice.common.model.SensorOperation.REGISTERED;
import static com.mprybicki.sensorservice.common.model.SensorOperation.UNREGISTERED;
import static com.mprybicki.sensorservice.common.model.SensorOperation.UPDATED;
import static com.mprybicki.sensorservice.common.model.SensorStatus.ACTIVE;
import static com.mprybicki.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
@Slf4j
public class RFSensorService implements SensorService {

    private RFSensorRepository rfSensorRepository;
    private RFSensorSenderService rfSensorSenderService;
    private RFSensorValidationService rfSensorValidationService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        rfSensorValidationService.validateRegisterSensor(sensor);
        rfSensorRepository.insert((RFSensor) sensor);
        rfSensorSenderService.send(new RFSensorDTO((RFSensor) sensor, REGISTERED));
        log.info("new rf sensor is registered " + sensor);
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        rfSensorValidationService.validateUnregisterSensor(id);
        RFSensor rfSensor = rfSensorRepository.findById(id).get();
        RFSensor inactiveRFSensor = RFSensor.builder()
                .sensorStatus(INACTIVE)
                .id(rfSensor.getId())
                .ip(rfSensor.getIp())
                .maxFrequency(rfSensor.getMaxFrequency())
                .minFrequency(rfSensor.getMinFrequency())
                .build();
        rfSensorRepository.save(inactiveRFSensor);
        rfSensorSenderService.send(new RFSensorDTO(inactiveRFSensor, UNREGISTERED));
        log.info("rf sensor is successfully unregistered " + inactiveRFSensor);
        return inactiveRFSensor;
    }

    @Override
    public Sensor updateSensor(String id, Sensor newSensor) {
        rfSensorValidationService.validateUpdateSensor(id, newSensor);
        rfSensorRepository.save((RFSensor) newSensor);
        rfSensorSenderService.send(new RFSensorDTO((RFSensor) newSensor, UPDATED));
        log.info("rf sensor is successfully updated " + newSensor);
        return newSensor;
    }

    public List<RFSensor> getActiveRFSensors() {
        return rfSensorRepository.findBySensorStatus(ACTIVE);
    }
}
