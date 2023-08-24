package com.doza.sensor.sensorapp.controllers;

import com.doza.sensor.sensorapp.security.PersonDetailsImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "page/hello";
    }

    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetailsImp personDetails = (PersonDetailsImp) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return personDetails.getUsername();
    }
}
