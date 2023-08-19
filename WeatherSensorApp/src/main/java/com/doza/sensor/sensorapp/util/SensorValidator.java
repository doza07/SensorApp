package com.doza.sensor.sensorapp.util;


import com.doza.sensor.sensorapp.model.Sensor;
import com.doza.sensor.sensorapp.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.findByName(sensor.getSensorName()).isPresent())
            errors.rejectValue("sensorName", "Уже есть сенсор с таким именем!");

    }
}
