package com.example.sensorservice.rfsensor.service;

import com.example.sensorservice.common.exception.InvalidSensorException;
import com.example.sensorservice.common.model.RFSensor;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.common.model.SensorStatus;
import com.example.sensorservice.rfsensor.repository.RFSensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctInactiveRFSensor;
import static com.example.sensorservice.rfsensor.sampledata.RFSensorSampleData.rfSensors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RFSensorServiceTest {

    @Mock
    private RFSensorRepository rfSensorRepository;

    @Mock
    private RFSensorSenderService rfSensorSenderService;

    @Mock
    private RFSensorValidationService rfSensorValidationService;

    @InjectMocks
    private RFSensorService rfSensorService;

    @Test
    void shouldRegisterRFSensor() {
        Sensor activeRFSensor = rfSensorService.registerSensor(correctActiveRFSensor());

        assertThat(activeRFSensor.getSensorStatus())
                .isEqualTo(SensorStatus.ACTIVE);
    }

    @Test
    void shouldUnregisterRFSensor() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));
        Sensor inactiveRFSensor = rfSensorService.unregisterSensor(correctActiveRFSensor().getId());

        assertThat(inactiveRFSensor)
                .usingRecursiveComparison()
                .isEqualTo(correctInactiveRFSensor());
    }

    @Test
    void shouldThrowExceptionWhenUnregisteringRFSensor() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rfSensorService.unregisterSensor(correctActiveRFSensor().getId()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("RF Sensor with given id does't exist");
    }

    @Test
    void shouldUpdateRFSensor() {
        when(rfSensorRepository.save(any())).thenReturn(correctActiveRFSensor());
        Sensor newRFSensor = rfSensorService.updateSensor("5ff8832b9d260a2bebb6a82d", correctActiveRFSensor());

        assertThat(newRFSensor)
                .usingRecursiveComparison()
                .isEqualTo(correctActiveRFSensor());
    }

    @Test
    void shouldGetListWithRFSensors() {
        when(rfSensorRepository.findAll()).thenReturn(rfSensors());
        List<RFSensor> rfSensors = rfSensorService.getRFSensors();

        assertThat(rfSensors)
                .usingRecursiveComparison()
                .isEqualTo(rfSensors());
    }

}