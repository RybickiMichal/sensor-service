package com.example.sensorservice.service;


import com.example.sensorservice.model.CameraDTO;
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
public class CameraSenderService {

    @Autowired
    private KafkaTemplate<String, CameraDTO> kafkaTemplate;

    @Value("${kafka.camera.topic}")
    private String kafkaTopic;

    public void send(CameraDTO cameraDTO) {
        ListenableFuture<SendResult<String, CameraDTO>> future = kafkaTemplate.send(kafkaTopic, cameraDTO);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, CameraDTO> result) {
                log.info("Sent message: " + cameraDTO.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + cameraDTO.toString(), ex);
            }
        });
    }
}