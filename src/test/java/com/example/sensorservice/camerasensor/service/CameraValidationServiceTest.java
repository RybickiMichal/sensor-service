package com.example.sensorservice.camerasensor.service;


import com.example.sensorservice.camerasensor.repository.CameraRepository;
import com.example.sensorservice.common.exception.InvalidSensorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        assertThatThrownBy(() -> cameraValidationService.validateIsCameraSensorExists(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Camera Sensor with given id does't exist");
    }

    @Test
    void shouldPassValidation() {
        when(cameraRepository.existsById(any())).thenReturn(TRUE);

        assertDoesNotThrow(() -> cameraValidationService.validateIsCameraSensorExists(any()));
    }

}