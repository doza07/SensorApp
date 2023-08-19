package com.doza.sensor.sensorapp.repositories;

import com.doza.sensor.sensorapp.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findBySensorName(String sensorName);
}
