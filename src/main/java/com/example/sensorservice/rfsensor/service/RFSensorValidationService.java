package com.example.sensorservice.rfsensor.service;

import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RFSensorValidationService {

    private RFSensorRepository rfSensorRepository;

    public void validateIsRFSensorExists(String id) {
        if (!rfSensorRepository.existsById(id)) {
            throw new InvalidSensorException("RF Sensor with given id does't exist");
        }
    }
}
