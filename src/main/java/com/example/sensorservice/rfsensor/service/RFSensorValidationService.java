package com.example.sensorservice.rfsensor.service;

import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.RFSensor;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class RFSensorValidationService {

    private RFSensorRepository rfSensorRepository;

    public void validateUnregisterSensor(String id) {
        RFSensor sensorToDelete = rfSensorRepository.findById(id).orElseThrow(() -> new InvalidSensorException("RF Sensor with given id doesn't exist"));
        validateIsSensorActive(sensorToDelete, "Cannot delete inactive sensor");
    }

    public void validateUpdateSensor(String oldSensorId, Sensor newSenor) {
        RFSensor oldSensor = rfSensorRepository.findById(oldSensorId).orElseThrow(() -> new InvalidSensorException("RF Sensor with given id doesn't exist"));
        validateIsSensorActive(oldSensor, "Cannot update inactive sensor");

        validateIsSensorActive(newSenor, "If you want unregister sensor then try delete endpoint");
    }

    private void validateIsSensorActive(Sensor sensor, String errorMessage) {
        if (sensor.getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException(errorMessage);
        }
    }
}
