package com.mprybicki.sensorservice.rfsensor.service;

import com.mprybicki.sensorservice.common.model.RFSensor;
import com.mprybicki.sensorservice.common.model.Sensor;
import com.mprybicki.sensorservice.common.model.SensorStatus;
import com.mprybicki.sensorservice.rfsensor.repository.RFSensorRepository;
import com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
import static com.mprybicki.sensorservice.rfsensor.sampledata.RFSensorSampleData.rfSensors;
import static org.assertj.core.api.Assertions.assertThat;
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
                .isEqualTo(RFSensorSampleData.correctInactiveRFSensor());
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
        when(rfSensorRepository.findBySensorStatus(any())).thenReturn(rfSensors());
        List<RFSensor> rfSensors = rfSensorService.getActiveRFSensors();

        assertThat(rfSensors)
                .usingRecursiveComparison()
                .isEqualTo(rfSensors());
    }

}