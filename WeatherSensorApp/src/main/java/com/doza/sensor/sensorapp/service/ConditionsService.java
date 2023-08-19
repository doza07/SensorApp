package com.doza.sensor.sensorapp.service;

import com.doza.sensor.sensorapp.model.Conditions;
import com.doza.sensor.sensorapp.repositories.ConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConditionsService {

    private final ConditionsRepository conditionsRepository;
    private final SensorService sensorService;


    public ConditionsService(ConditionsRepository conditionsRepository, SensorService sensorService) {
        this.conditionsRepository = conditionsRepository;
        this.sensorService = sensorService;
    }

    public List<Conditions> findAll() {
        return conditionsRepository.findAll();
    }

    @Transactional
    public void addConditions(Conditions conditions) {
        enrichConditions(conditions);
        conditionsRepository.save(conditions);
    }

    public void enrichConditions(Conditions conditions) {
        conditions.setSensor(sensorService.findByName(conditions.getSensor().getSensorName()).get());
        conditions.setDataTime(LocalDateTime.now());
    }


}
