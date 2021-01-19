package com.mprybicki.sensorservice.rfsensor.service;


import com.mprybicki.sensorservice.common.model.RFSensorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class RFSensorSenderService {

    private KafkaTemplate<String, RFSensorDTO> kafkaTemplate;

    @Value("${kafka.rf.sensor.topic}")
    private String kafkaTopic;

    public RFSensorSenderService(KafkaTemplate<String, RFSensorDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(RFSensorDTO rfSensorDTO) {
        ListenableFuture<SendResult<String, RFSensorDTO>> future = kafkaTemplate.send(kafkaTopic, rfSensorDTO);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, RFSensorDTO> result) {
                log.info("Sent message: " + rfSensorDTO.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + rfSensorDTO.toString(), ex);
            }
        });
    }
}