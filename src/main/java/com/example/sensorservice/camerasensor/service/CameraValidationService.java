package com.example.sensorservice.camerasensor.service;

import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.Camera;
import com.example.sensorservice.common.model.Sensor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class CameraValidationService {

    private CameraRepository cameraRepository;

    public void validateUnregisterSensor(String id) {
        Camera sensorToDelete = cameraRepository.findById(id).orElseThrow(() -> new InvalidSensorException("Camera with given id doesn't exist"));
        validateIsSensorActive(sensorToDelete, "Cannot delete inactive sensor");
    }

    public void validateUpdateSensor(String oldSensorId, Sensor newSenor) {
        Camera oldSensor = cameraRepository.findById(oldSensorId).orElseThrow(() -> new InvalidSensorException("Camera with given id doesn't exist"));
        validateIsSensorActive(oldSensor, "Cannot update inactive sensor");

        validateIsSensorActive(newSenor, "If you want unregister sensor then try delete endpoint");
    }

    private void validateIsSensorActive(Sensor sensor, String errorMessage) {
        if (sensor.getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException(errorMessage);
        }
    }
}
