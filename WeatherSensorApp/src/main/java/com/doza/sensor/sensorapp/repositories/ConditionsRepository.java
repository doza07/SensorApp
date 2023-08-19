package com.doza.sensor.sensorapp.repositories;

import com.doza.sensor.sensorapp.model.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Integer> {
}
