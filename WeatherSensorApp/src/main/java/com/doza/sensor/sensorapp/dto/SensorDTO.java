package com.doza.sensor.sensorapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class SensorDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 15, message = "Name should be between 3-15 charters")
    private String sensorName;

    @NotEmpty(message = "Location should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2-30 charters")
    private String sensorLocation;


    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorLocation() {
        return sensorLocation;
    }

    public void setSensorLocation(String sensorLocation) {
        this.sensorLocation = sensorLocation;
    }
}
