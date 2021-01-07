package com.example.sensorservice.repository;

import com.example.sensorservice.model.RFSensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFSensorRepository extends MongoRepository<RFSensor, String> {}
