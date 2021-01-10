package com.mprybicki.sensorservice.camerasensor.service;

import com.mprybicki.sensorservice.camerasensor.repository.CameraRepository;
import com.mprybicki.sensorservice.common.exception.InvalidSensorException;
import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.Sensor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mprybicki.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class CameraValidationService {

    private CameraRepository cameraRepository;

    public void validateRegisterSensor(Sensor sensorToRegister) {
        validateIsSensorIpDistinct(null, sensorToRegister, "Sensor with given IP is already registered, try to unregister it");
    }

    public void validateUnregisterSensor(String id) {
        Camera sensorToUnregister = cameraRepository.findById(id).orElseThrow(() -> new InvalidSensorException("Camera with given id doesn't exist"));
        validateIsSensorActive(sensorToUnregister, "Cannot delete inactive sensor");
    }

    public void validateUpdateSensor(String oldSensorId, Sensor newSenor) {
        Camera oldSensor = cameraRepository.findById(oldSensorId).orElseThrow(() -> new InvalidSensorException("Camera with given id doesn't exist"));
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
        if (cameraRepository.existsSensorById(newSensor.getId())) {
            throw new InvalidSensorException(errorMessage);
        }
    }
}
