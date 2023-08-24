package com.doza.sensor.sensorapp.service;

import com.doza.sensor.sensorapp.model.Person;
import com.doza.sensor.sensorapp.repositories.PersonRepository;
import com.doza.sensor.sensorapp.security.PersonDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;


    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = personRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Person not found");

        return new PersonDetailsImp(user.get()) {
        };
    }
}
