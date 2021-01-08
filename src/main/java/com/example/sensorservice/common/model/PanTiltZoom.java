package com.example.sensorservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PanTiltZoom {
    private Double pan;
    private Double tilt;
    private Double zoom;
}
