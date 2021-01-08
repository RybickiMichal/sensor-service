package com.example.sensorservice.camerasensor.service;

import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.Camera;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.common.model.SensorStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.sensorservice.camerasensor.sampledata.CameraSampleData.cameraSensors;
import static com.example.sensorservice.camerasensor.sampledata.CameraSampleData.correctActiveCamera;
import static com.example.sensorservice.camerasensor.sampledata.CameraSampleData.correctInactiveCamera;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CameraServiceTest {

    @Mock
    private CameraRepository cameraRepository;

    @Mock
    private CameraSenderService cameraSenderService;

    @Mock
    private CameraValidationService cameraValidationService;

    @InjectMocks
    private CameraService cameraService;

    @Test
    void shouldRegisterCamera() {
        Sensor activeCameraSensor = cameraService.registerSensor(correctActiveCamera());

        assertThat(activeCameraSensor.getSensorStatus())
                .isEqualTo(SensorStatus.ACTIVE);
    }

    @Test
    void shouldUnregisterCamera() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));
        Sensor inactiveCamera = cameraService.unregisterSensor(correctActiveCamera().getId());

        assertThat(inactiveCamera)
                .usingRecursiveComparison()
                .isEqualTo(correctInactiveCamera());
    }

    @Test
    void shouldThrowExceptionWhenUnregisteringAndThereIsNoGivenSensor() {
        when(cameraRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cameraService.unregisterSensor(correctActiveCamera().getId()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Camera Sensor with given id does't exist");
    }

    @Test
    void shouldUpdateCamera() {
        when(cameraRepository.save(any())).thenReturn(correctActiveCamera());
        Sensor newCamera = cameraService.updateSensor("5ff8832b9d260a2bebb6a82d", correctActiveCamera());

        assertThat(newCamera)
                .usingRecursiveComparison()
                .isEqualTo(correctActiveCamera());
    }

    @Test
    void shouldGetListWithCameraSensors() {
        when(cameraRepository.findAll()).thenReturn(cameraSensors());
        List<Camera> cameraSensors = cameraService.getCameraSensors();

        assertThat(cameraSensors)
                .usingRecursiveComparison()
                .isEqualTo(cameraSensors());
    }

}