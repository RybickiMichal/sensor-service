package com.mprybicki.sensorservice.rfsensor.service;


import com.mprybicki.sensorservice.common.exception.*;
import com.mprybicki.sensorservice.rfsensor.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.*;
import static java.lang.Boolean.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        when(rfSensorRepository.existsSensorByIp(any())).thenReturn(TRUE);
        when(rfSensorRepository.findByIp(any())).thenReturn(List.of(correctActiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUpdateSensor(any(), correctActiveRFSensor2()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Sensor with given IP is already registered, try to unregister it");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenRegisteringAndNewSensorIpIsNotDistinct() {
        when(rfSensorRepository.existsSensorByIp(any())).thenReturn(TRUE);
        when(rfSensorRepository.findByIp(any())).thenReturn(List.of(correctActiveRFSensor()));

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