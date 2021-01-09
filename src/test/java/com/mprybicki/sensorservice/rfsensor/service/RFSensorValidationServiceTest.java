package com.mprybicki.sensorservice.rfsensor.service;


import com.mprybicki.sensorservice.common.exception.InvalidSensorException;
import com.mprybicki.sensorservice.rfsensor.repository.RFSensorRepository;
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
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(RFSensorSampleData.correctInactiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot delete inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndOldSensorStatusIsInactive() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(RFSensorSampleData.correctInactiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), null))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot update inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUpdatingAndNewSensorStatusIsInactive() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(RFSensorSampleData.correctActiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), RFSensorSampleData.correctInactiveRFSensor()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("If you want unregister sensor then try delete endpoint");
    }

    @Test
    void shouldPassUnregisteringValidation() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(RFSensorSampleData.correctActiveRFSensor()));

        assertDoesNotThrow(() -> rfSensorValidationService.validateUnregisterSensor(any()));
    }

    @Test
    void shouldPassUpdatingValidation() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(RFSensorSampleData.correctActiveRFSensor()));

        assertDoesNotThrow(() -> rfSensorValidationService.validateUpdateSensor(any(), RFSensorSampleData.correctActiveRFSensor()));
    }


}