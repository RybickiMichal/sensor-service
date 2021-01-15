package com.mprybicki.sensorservice.rfsensor.repository;

import com.mprybicki.sensorservice.common.model.RFSensor;
import com.mprybicki.sensorservice.common.model.SensorStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RFSensorRepository extends MongoRepository<RFSensor, String> {

    List<RFSensor> findBySensorStatus(SensorStatus sensorStatus);

    boolean existsSensorByIpAndSensorStatus(String ip, SensorStatus sensorStatus);

}
