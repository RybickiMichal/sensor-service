package com.mprybicki.sensorservice.camerasensor.repository;

import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.SensorStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends MongoRepository<Camera, String> {

    List<Camera> findBySensorStatus(SensorStatus sensorStatus);

    boolean existsSensorByIp(String model);

    boolean existsByIpAndSensorStatus(String ip, SensorStatus sensorStatus);

    Camera findByIdAndSensorStatus(String id, SensorStatus sensorStatus);

    Optional<Camera> findFirstByCameraServicePortIsNull();
}
