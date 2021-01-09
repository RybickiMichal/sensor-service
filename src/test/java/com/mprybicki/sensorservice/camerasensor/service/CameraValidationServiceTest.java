package com.mprybicki.sensorservice.camerasensor.service;


import com.mprybicki.sensorservice.camerasensor.repository.CameraRepository;
import com.mprybicki.sensorservice.common.exception.InvalidSensorException;
import com.mprybicki.sensorservice.camerasensor.sampledata.CameraSampleData;
import com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndThereIsNoSensorWithGivenId() {
        assertThatThrownBy(() -> cameraValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Camera with given id doesn't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndThereIsNoSensorWithGivenId() {
        assertThatThrownBy(() -> cameraValidationService.validateUpdateSensor(any(), null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Camera with given id doesn't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndSensorStatusIsInactive() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(CameraSampleData.correctInactiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot delete inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndOldSensorStatusIsInactive() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(CameraSampleData.correctInactiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateUpdateSensor(any(), null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot update inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndNewSensorStatusIsInactive() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(CameraSampleData.correctActiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateUpdateSensor(any(), CameraSampleData.correctInactiveCamera()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("If you want unregister sensor then try delete endpoint");
    }

    @Test
    void shouldPassUnregisteringValidation() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(CameraSampleData.correctActiveCamera()));

        assertDoesNotThrow(() -> cameraValidationService.validateUnregisterSensor(any()));
    }

    @Test
    void shouldPassUpdatingValidation() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(CameraSampleData.correctActiveCamera()));

        assertDoesNotThrow(() -> cameraValidationService.validateUpdateSensor(any(), RFSensorSampleData.correctActiveRFSensor()));
    }

}