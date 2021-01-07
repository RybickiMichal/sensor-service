package com.example.sensorservice.repository;

import com.example.sensorservice.model.Camera;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends MongoRepository<Camera, String> {}
