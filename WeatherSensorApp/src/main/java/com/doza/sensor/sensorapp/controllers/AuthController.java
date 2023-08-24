package com.doza.sensor.sensorapp.controllers;


import com.doza.sensor.sensorapp.dto.AuthDTO;
import com.doza.sensor.sensorapp.dto.PersonDTO;
import com.doza.sensor.sensorapp.model.Person;
import com.doza.sensor.sensorapp.security.JWTUtil;
import com.doza.sensor.sensorapp.service.PersonRegistrationService;
import com.doza.sensor.sensorapp.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static com.doza.sensor.sensorapp.util.Error.returnErrorsToClient;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersonRegistrationService personRegistrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthDTO authDTO;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonRegistrationService personRegistrationService, PersonValidator personValidator,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthDTO authDTO, AuthenticationManager authenticationManager) {
        this.personRegistrationService = personRegistrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authDTO = authDTO;
        this.authenticationManager = authenticationManager;
    }


//        @GetMapping("/login")
//        public String loginPage() {
//            return "auth/login";
//        }
//
//        @GetMapping("/registration")
//        public String registrationPage(@ModelAttribute("person") Person person) {
//            return "auth/registration";
//        }

        @PostMapping("/registration")
        public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                       BindingResult bindingResult) {

        Person person = convernToPerson(personDTO);

            personValidator.validate(person, bindingResult);

            if (bindingResult.hasErrors())
                returnErrorsToClient(bindingResult);


            personRegistrationService.register(person);

            String token = jwtUtil.generateToken(person.getUsername());
            return Map.of("jwt-token", token);
        }

        @PostMapping("/login")
        public Map<String, String> performLogin(@RequestBody AuthDTO authDTO) {
            UsernamePasswordAuthenticationToken authInputToken
                    = new UsernamePasswordAuthenticationToken(authDTO.getUsername(),
                    authDTO.getPassword());

            try {
                authenticationManager.authenticate(authInputToken);
            } catch (BadCredentialsException e) {
                return Map.of("message", "Incorrect credentials");
            }

            String token = jwtUtil.generateToken(authDTO.getUsername());
            return Map.of("jwt-token", token);
        }

        public Person convernToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
        }
    }
