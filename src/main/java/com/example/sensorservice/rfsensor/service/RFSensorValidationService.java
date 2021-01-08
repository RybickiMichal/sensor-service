package com.example.sensorservice.rfsensor.service;

import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.common.model.SensorStatus;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class RFSensorValidationService {

    private RFSensorRepository rfSensorRepository;

    public void validateIsRFSensorExistsAndSensorStatusIsActive(String id) {
        if (!rfSensorRepository.existsById(id)) {
            throw new InvalidSensorException("RF Sensor with given id does't exist");
        }
        if (rfSensorRepository.findById(id).get().getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException("Cannot update/delete inactive sensor");
        }
    }

    public void validateRFSensorStatus(Sensor sensor) {
        if(sensor.getSensorStatus().equals(INACTIVE)){
            throw new InvalidSensorException("If you want unregister sensor then try delete endpoint");
        }
    }
}
