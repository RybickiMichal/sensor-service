package com.example.sensorservice.camerasensor.service;

import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.Sensor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.sensorservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class CameraValidationService {

    private CameraRepository cameraRepository;

    public void validateIsCameraSensorExistsAndSensorStatusIsActive(String id) {
        if (!cameraRepository.existsById(id)) {
            throw new InvalidSensorException("Camera Sensor with given id does't exist");
        }
        if (cameraRepository.findById(id).get().getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException("Cannot update/delete inactive sensor");
        }
    }

    public void validateCameraSensorStatus(Sensor sensor) {
        if(sensor.getSensorStatus().equals(INACTIVE)){
            throw new InvalidSensorException("If you want unregister sensor then try delete endpoint");
        }
    }
}
