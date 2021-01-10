package com.mprybicki.sensorservice.rfsensor.service;

import com.mprybicki.sensorservice.common.exception.InvalidSensorException;
import com.mprybicki.sensorservice.common.model.RFSensor;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mprybicki.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class RFSensorValidationService {

    private RFSensorRepository rfSensorRepository;

    public void validateRegisterSensor(Sensor sensorToRegister) {
        validateIsSensorIpDistinct(null, sensorToRegister, "Sensor with given IP is already registered, try to unregister it");
    }

    public void validateUnregisterSensor(String id) {
        RFSensor sensorToUnregister = rfSensorRepository.findById(id).orElseThrow(() -> new InvalidSensorException("RF Sensor with given id doesn't exist"));
        validateIsSensorActive(sensorToUnregister, "Cannot delete inactive sensor");
    }

    public void validateUpdateSensor(String oldSensorId, Sensor newSenor) {
        RFSensor oldSensor = rfSensorRepository.findById(oldSensorId).orElseThrow(() -> new InvalidSensorException("RF Sensor with given id doesn't exist"));
        validateIsSensorActive(oldSensor, "Cannot update inactive sensor");

        validateIsSensorActive(newSenor, "To unregister sensor try delete endpoint");
        validateIsSensorIpDistinct(oldSensor, newSenor, "Sensor with given IP is already registered, try to unregister it");
    }

    private void validateIsSensorActive(Sensor sensor, String errorMessage) {
        if (sensor.getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException(errorMessage);
        }
    }

    private void validateIsSensorIpDistinct(Sensor oldSensor, Sensor newSensor, String errorMessage) {
        if (oldSensor != null && oldSensor.getIp().equals(newSensor.getIp())) {
            return;
        }
        if (rfSensorRepository.existsSensorByIp(newSensor.getIp())) {
            throw new InvalidSensorException(errorMessage);
        }
    }
}
