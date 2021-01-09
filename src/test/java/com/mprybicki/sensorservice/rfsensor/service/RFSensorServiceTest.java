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
        Sensor activeRFSensor = rfSensorService.registerSensor(RFSensorSampleData.correctActiveRFSensor());

        assertThat(activeRFSensor.getSensorStatus())
                .isEqualTo(SensorStatus.ACTIVE);
    }

    @Test
    void shouldUnregisterRFSensor() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(RFSensorSampleData.correctActiveRFSensor()));
        Sensor inactiveRFSensor = rfSensorService.unregisterSensor(RFSensorSampleData.correctActiveRFSensor().getId());

        assertThat(inactiveRFSensor)
                .usingRecursiveComparison()
                .isEqualTo(RFSensorSampleData.correctInactiveRFSensor());
    }

    @Test
    void shouldUpdateRFSensor() {
        when(rfSensorRepository.save(any())).thenReturn(RFSensorSampleData.correctActiveRFSensor());
        Sensor newRFSensor = rfSensorService.updateSensor("5ff8832b9d260a2bebb6a82d", RFSensorSampleData.correctActiveRFSensor());

        assertThat(newRFSensor)
                .usingRecursiveComparison()
                .isEqualTo(RFSensorSampleData.correctActiveRFSensor());
    }

    @Test
    void shouldGetListWithRFSensors() {
        when(rfSensorRepository.findAll()).thenReturn(RFSensorSampleData.rfSensors());
        List<RFSensor> rfSensors = rfSensorService.getActiveRFSensors();

        assertThat(rfSensors)
                .usingRecursiveComparison()
                .isEqualTo(RFSensorSampleData.rfSensors());
    }

}