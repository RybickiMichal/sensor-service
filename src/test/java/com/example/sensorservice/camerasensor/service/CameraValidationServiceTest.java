package com.example.sensorservice.camerasensor.service;


import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.exception.InvalidSensorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.sensorservice.camerasensor.sampledata.CameraSampleData.correctActiveCamera;
import static com.example.sensorservice.camerasensor.sampledata.CameraSampleData.correctInactiveCamera;
import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctInactiveRFSensor;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CameraValidationServiceTest {

    @Mock
    private CameraRepository cameraRepository;

    @InjectMocks
    private CameraValidationService cameraValidationService;

    @Test
    void shouldThrowInvalidSensorExceptionWhenThereIsNoSensorWithGivenId() {
        when(cameraRepository.existsById(any())).thenReturn(FALSE);

        assertThatThrownBy(() -> cameraValidationService.validateIsCameraSensorExistsAndSensorStatusIsActive(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Camera Sensor with given id does't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenOldSensorStatusIsInactive() {
        when(cameraRepository.existsById(any())).thenReturn(TRUE);
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctInactiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateIsCameraSensorExistsAndSensorStatusIsActive(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot update/delete inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenNewSensorStatusIsInactive() {
        assertThatThrownBy(() -> cameraValidationService.validateCameraSensorStatus(correctInactiveRFSensor()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("If you want unregister sensor then try delete endpoint");
    }

    @Test
    void shouldPassValidation() {
        when(cameraRepository.existsById(any())).thenReturn(TRUE);
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));

        assertDoesNotThrow(() -> cameraValidationService.validateIsCameraSensorExistsAndSensorStatusIsActive(any()));
        assertDoesNotThrow(() -> cameraValidationService.validateCameraSensorStatus(correctActiveRFSensor()));
    }

}