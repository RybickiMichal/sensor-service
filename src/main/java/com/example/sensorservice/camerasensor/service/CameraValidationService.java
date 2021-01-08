package com.example.sensorservice.camerasensor.service;

import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.exception.InvalidSensorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CameraValidationService {

    private CameraRepository cameraRepository;

    public void validateIsCameraSensorExists(String id) {
        if (!cameraRepository.existsById(id)) {
            throw new InvalidSensorException("Camera Sensor with given id does't exist");
        }
    }
}
