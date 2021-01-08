package com.example.sensorservice.rfsensor.repository;

import com.example.sensorservice.common.model.RFSensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFSensorRepository extends MongoRepository<RFSensor, String> {}
