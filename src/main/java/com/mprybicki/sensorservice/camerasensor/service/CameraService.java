package com.mprybicki.sensorservice.camerasensor.service;

import com.mprybicki.sensorservice.camerasensor.repository.CameraRepository;
import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.CameraDTO;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.common.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mprybicki.sensorservice.common.model.SensorOperation.REGISTERED;
import static com.mprybicki.sensorservice.common.model.SensorOperation.UNREGISTERED;
import static com.mprybicki.sensorservice.common.model.SensorOperation.UPDATED;
import static com.mprybicki.sensorservice.common.model.SensorStatus.ACTIVE;
import static com.mprybicki.sensorservice.common.model.SensorStatus.INACTIVE;


@AllArgsConstructor
@Service
public class CameraService implements SensorService {

    private CameraRepository cameraRepository;
    private CameraSenderService cameraSenderService;
    private CameraValidationService cameraValidationService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        cameraValidationService.validateRegisterSensor(sensor);
        cameraRepository.insert((Camera) sensor);
        cameraSenderService.send(new CameraDTO((Camera) sensor, REGISTERED));
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        cameraValidationService.validateUnregisterSensor(id);
        Camera camera = cameraRepository.findById(id).get();
        Camera inactiveCamera = Camera.builder()
                .sensorStatus(INACTIVE)
                .id(camera.getId())
                .ip(camera.getIp())
                .panTiltZoom(camera.getPanTiltZoom())
                .streamAddress(camera.getStreamAddress())
                .build();
        cameraRepository.save(inactiveCamera);
        cameraSenderService.send(new CameraDTO(inactiveCamera, UNREGISTERED));
        return inactiveCamera;
    }

    @Override
    public Sensor updateSensor(String id, Sensor newSensor) {
        cameraValidationService.validateUpdateSensor(id, newSensor);
        cameraRepository.save((Camera) newSensor);
        cameraSenderService.send(new CameraDTO((Camera) newSensor, UPDATED));
        return newSensor;
    }

    public List<Camera> getActiveCameraSensors() {
        return cameraRepository.findBySensorStatus(ACTIVE);
    }
}
