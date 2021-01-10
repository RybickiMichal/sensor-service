package com.mprybicki.sensorservice.rfsensor.service;


import com.mprybicki.sensorservice.common.exception.InvalidSensorException;
import com.mprybicki.sensorservice.rfsensor.repository.RFSensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor2;
import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctInactiveRFSensor;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RFSensorValidationServiceTest {

    @Mock
    private RFSensorRepository rfSensorRepository;

    @InjectMocks
    private RFSensorValidationService rfSensorValidationService;

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndThereIsNoSensorWithGivenId() {
        assertThatThrownBy(() -> rfSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("RF Sensor with given id doesn't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndThereIsNoSensorWithGivenId() {
        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("RF Sensor with given id doesn't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndSensorStatusIsInactive() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctInactiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot delete inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndOldSensorStatusIsInactive() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctInactiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot update inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndNewSensorStatusIsInactive() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), correctInactiveRFSensor()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("To unregister sensor try delete endpoint");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndNewSensorIpIsNotDistinct() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));
        when(rfSensorRepository.existsSensorById(any())).thenReturn(TRUE);

        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), correctActiveRFSensor2()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Sensor with given IP is already registered, try to unregister it");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenRegisteringAndNewSensorIpIsNotDistinct() {
        when(rfSensorRepository.existsSensorById(any())).thenReturn(TRUE);

        assertThatThrownBy(() -> rfSensorValidationService.validateRegisterSensor(correctActiveRFSensor()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Sensor with given IP is already registered, try to unregister it");
    }

    @Test
    void shouldPassUnregisteringValidation() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));

        assertDoesNotThrow(() -> rfSensorValidationService.validateUnregisterSensor(any()));
    }

    @Test
    void shouldPassUpdatingValidation() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));

        assertDoesNotThrow(() -> rfSensorValidationService.validateUpdateSensor(any(), correctActiveRFSensor()));
    }


}