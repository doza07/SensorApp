package com.doza.sensor.sensorapp.util;

import com.doza.sensor.sensorapp.model.Conditions;
import com.doza.sensor.sensorapp.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ConditionsValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public ConditionsValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Conditions conditions = (Conditions) target;

        if (conditions.getSensor() == null) {
            return;
        }

        if (sensorService.findByName(conditions.getSensor().getSensorName()).isEmpty())
            errors.rejectValue("sensor", "Not found Sensor");
    }
}
