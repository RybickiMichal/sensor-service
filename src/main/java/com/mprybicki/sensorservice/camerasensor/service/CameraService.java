package com.mprybicki.sensorservice.camerasensor.service;

import com.mprybicki.sensorservice.camerasensor.repository.CameraRepository;
import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.CameraDTO;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.common.model.SensorOperation;
import com.mprybicki.sensorservice.common.model.SensorStatus;
import com.mprybicki.sensorservice.common.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class CameraService implements SensorService {

    private CameraRepository cameraRepository;
    private CameraSenderService cameraSenderService;
    private CameraValidationService cameraValidationService;

    @Override
    public Sensor registerSensor(Sensor sensor) {
        cameraRepository.insert((Camera) sensor);
        cameraSenderService.send(new CameraDTO((Camera) sensor, SensorOperation.REGISTERED));
        return sensor;
    }

    @Override
    public Sensor unregisterSensor(String id) {
        cameraValidationService.validateUnregisterSensor(id);
        Camera camera = cameraRepository.findById(id).get();
        Camera inactiveCamera = Camera.builder()
                .sensorStatus(SensorStatus.INACTIVE)
                .id(camera.getId())
                .ip(camera.getIp())
                .panTiltZoom(camera.getPanTiltZoom())
                .streamAddress(camera.getStreamAddress())
                .build();
        cameraRepository.save(inactiveCamera);
        cameraSenderService.send(new CameraDTO(inactiveCamera, SensorOperation.UNREGISTERED));
        return inactiveCamera;
    }

    @Override
    public Sensor updateSensor(String id, Sensor newSensor) {
        cameraValidationService.validateUpdateSensor(id, newSensor);
        cameraRepository.save((Camera) newSensor);
        cameraSenderService.send(new CameraDTO((Camera) newSensor, SensorOperation.UPDATED));
        return newSensor;
    }

    public List<Camera> getCameraSensors() {
        return cameraRepository.findAll();
    }
}
