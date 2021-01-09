package com.mprybicki.sensorservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public class PanTiltZoom {

    @Min(0)
    @Max(360)
    @NotNull
    private Double pan;

    @Min(0)
    @Max(360)
    @NotNull
    private Double tilt;

    @Min(0)
    @NotNull
    private Double zoom;
}
