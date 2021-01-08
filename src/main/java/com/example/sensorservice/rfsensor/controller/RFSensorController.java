package com.example.sensorservice.rfsensor.controller;

import com.example.sensorservice.common.model.RFSensor;
import com.example.sensorservice.common.model.Sensor;
import com.example.sensorservice.rfsensor.service.RFSensorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class RFSensorController {

    private RFSensorService rfSensorService;

    @PostMapping(value = "/rf-sensor/register")
    public Sensor registerRFSensor(@RequestBody RFSensor rfSensor) {
        return rfSensorService.registerSensor(rfSensor);
    }

    @DeleteMapping(value = "/rf-sensor/unregister/{id}")
    public Sensor unregisterRFSensor(@PathVariable String id) {
        return rfSensorService.unregisterSensor(id);
    }

    @PutMapping(value = "/rf-sensor/update/{id}")
    public Sensor updateRFSensor(@PathVariable String id, @RequestBody RFSensor rfSensor) {
        return rfSensorService.updateSensor(id, rfSensor);
    }

    @GetMapping(value = "/rf-sensors")
    public List<RFSensor> getRFSensors() {
        return rfSensorService.getRFSensors();
    }

}
