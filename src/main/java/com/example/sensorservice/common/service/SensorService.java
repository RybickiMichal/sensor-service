package com.example.sensorservice.common.service;

import com.example.sensorservice.common.model.Sensor;

public interface SensorService {

    Sensor registerSensor(Sensor sensor);

    Sensor unregisterSensor(String id);

    Sensor updateSensor(String id, Sensor sensor);

}
