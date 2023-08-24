package com.doza.sensor.sensorapp.service;

import com.doza.sensor.sensorapp.model.Person;
import com.doza.sensor.sensorapp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonRegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonRegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }
}
