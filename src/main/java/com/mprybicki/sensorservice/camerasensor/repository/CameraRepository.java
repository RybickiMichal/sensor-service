package com.mprybicki.sensorservice.camerasensor.repository;

import com.mprybicki.sensorservice.common.model.Camera;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends MongoRepository<Camera, String> {}
