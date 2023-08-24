package com.doza.sensor.sensorapp.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class AuthDTO {
    @NotNull(message = "Can't be empty")
    @Size(max = 30, message = "Max size 30")
    private String username;
    @NotNull(message = "Can't be empty")
    @Size(max = 100, message = "Max size 100")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
