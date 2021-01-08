package com.example.sensorservice.rfsensor.service;


import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctInactiveRFSensor;
import static java.lang.Boolean.FALSE;
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
    void shouldThrowInvalidSensorExceptionWhenThereIsNoSensorWithGivenId() {
        when(rfSensorRepository.existsById(any())).thenReturn(FALSE);

        assertThatThrownBy(() -> rfSensorValidationService.validateIsRFSensorExistsAndSensorStatusIsActive(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("RF Sensor with given id does't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenOldSensorStatusIsInactive() {
        when(rfSensorRepository.existsById(any())).thenReturn(TRUE);
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctInactiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateIsRFSensorExistsAndSensorStatusIsActive(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot update/delete inactive sensor");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenNewSensorStatusIsInactive() {
        assertThatThrownBy(() -> rfSensorValidationService.validateRFSensorStatus(correctInactiveRFSensor()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("If you want unregister sensor then try delete endpoint");
    }

    @Test
    void shouldPassValidation() {
        when(rfSensorRepository.existsById(any())).thenReturn(TRUE);
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));

        assertDoesNotThrow(() -> rfSensorValidationService.validateIsRFSensorExistsAndSensorStatusIsActive(any()));
        assertDoesNotThrow(() -> rfSensorValidationService.validateRFSensorStatus(correctActiveRFSensor()));
    }


}