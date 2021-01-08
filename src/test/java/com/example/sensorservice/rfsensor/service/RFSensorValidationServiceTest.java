package com.example.sensorservice.rfsensor.service;


import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
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
class RFSensorValidationServiceTest {

    @Mock
    private RFSensorRepository rfSensorRepository;

    @InjectMocks
    private RFSensorValidationService rfSensorValidationService;

    @Test
    void shouldThrowInvalidSensorExceptionWhenThereIsNoSensorWithGivenId() {
        when(rfSensorRepository.existsById(any())).thenReturn(FALSE);

        assertThatThrownBy(() -> rfSensorValidationService.validateIsRFSensorExists(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("RF Sensor with given id does't exist");
    }

    @Test
    void shouldPassValidation() {
        when(rfSensorRepository.existsById(any())).thenReturn(TRUE);

        assertDoesNotThrow(() -> rfSensorValidationService.validateIsRFSensorExists(any()));
    }

}