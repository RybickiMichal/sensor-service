package com.mprybicki.sensorservice.camerasensor.service;


import com.mprybicki.sensorservice.camerasensor.repository.CameraRepository;
import com.mprybicki.sensorservice.common.exception.InvalidSensorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mprybicki.sensorservice.camerasensor.sampledata.CameraSampleData.correctActiveCamera;
import static com.mprybicki.sensorservice.camerasensor.sampledata.CameraSampleData.correctActiveCamera2;
import static com.mprybicki.sensorservice.camerasensor.sampledata.CameraSampleData.correctActiveCameraRegisteredToCameraSensor;
import static com.mprybicki.sensorservice.camerasensor.sampledata.CameraSampleData.correctInactiveCamera;
import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
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
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctInactiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot delete inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndOldSensorStatusIsInactive() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctInactiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateUpdateSensor(any(), null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot update inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndNewSensorStatusIsInactive() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));

        assertThatThrownBy(() -> cameraValidationService.validateUpdateSensor(any(), correctInactiveCamera()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("To unregister sensor try delete endpoint");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndNewSensorIpIsNotDistinct() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));
        when(cameraRepository.existsSensorByIp(any())).thenReturn(TRUE);
        when(cameraRepository.existsByIpAndSensorStatus(any(), any())).thenReturn(TRUE);

        assertThatThrownBy(() -> cameraValidationService.validateUpdateSensor(any(), correctActiveCamera2()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Sensor with given IP is already registered, try to unregister it");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenRegisteringAndNewSensorIpIsNotDistinct() {
        when(cameraRepository.existsSensorByIp(any())).thenReturn(TRUE);
        when(cameraRepository.existsByIpAndSensorStatus(any(), any())).thenReturn(TRUE);

        assertThatThrownBy(() -> cameraValidationService.validateRegisterSensor(correctActiveCamera()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Sensor with given IP is already registered, try to unregister it");
    }

    @Test
    void shouldPassUnregisteringValidation() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));

        assertDoesNotThrow(() -> cameraValidationService.validateUnregisterSensor(any()));
    }

    @Test
    void shouldPassUpdatingValidation() {
        when(cameraRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));

        assertDoesNotThrow(() -> cameraValidationService.validateUpdateSensor(any(), correctActiveRFSensor()));
    }

    @Test
    void shouldPassRegisteringCameraServiceToCameraSensorValidation() {
        assertDoesNotThrow(() -> cameraValidationService.validateRegisterCameraServiceToCameraSensor(correctActiveCamera()));
    }

    @Test
    void shouldNotPassRegisteringCameraServiceToCameraSensorWhenGivenCameraDoesNotExistValidation() {
        assertThatThrownBy(() -> cameraValidationService.validateRegisterCameraServiceToCameraSensor(null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot register camera service to camera sensor. There is no sensor with given ip");
    }

    @Test
    void shouldNotPassRegisteringCameraServiceToCameraSensorWhenGivenCameraHasAlreadyRegisteredService() {
        assertThatThrownBy(() -> cameraValidationService.validateRegisterCameraServiceToCameraSensor(correctActiveCameraRegisteredToCameraSensor()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("There is already registered camera service to this camera sensor");
    }

}