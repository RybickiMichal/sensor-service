package com.example.sensorservice.service;

import com.example.sensorservice.model.Sensor;

public interface SensorService {

    Sensor registerSensor(Sensor sensor);

    void unregisterSensor(String id);

    Sensor updateSensor(String id, Sensor sensor);

}
