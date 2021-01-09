package com.mprybicki.sensorservice.common.service;

import com.mprybicki.sensorservice.common.model.Sensor;

public interface SensorService {

    Sensor registerSensor(Sensor sensor);

    Sensor unregisterSensor(String id);

    Sensor updateSensor(String id, Sensor sensor);

}
