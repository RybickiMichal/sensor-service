package com.example.sensorservice.camerasensor.repository;

import com.example.sensorservice.common.model.Camera;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends MongoRepository<Camera, String> {}
