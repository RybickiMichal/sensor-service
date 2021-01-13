package com.mprybicki.sensorservice.camerasensor.repository;

import com.mprybicki.sensorservice.common.model.Camera;
import com.mprybicki.sensorservice.common.model.SensorStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends MongoRepository<Camera, String> {

    List<Camera> findBySensorStatus(SensorStatus sensorStatus);

    boolean existsSensorByIp(String model);

    boolean existsByIpAndSensorStatus(String ip, SensorStatus sensorStatus);

    Camera findByIpAndSensorStatus(String ip, SensorStatus sensorStatus);
}
