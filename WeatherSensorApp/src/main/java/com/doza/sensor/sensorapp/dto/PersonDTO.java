package com.doza.sensor.sensorapp.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonDTO {

    @NotNull(message = "Can't be empty")
    @Size(max = 30, message = "Max size 30")
    private String username;
    private String password;
    @Email
    @NotNull(message = "Can't be empty")
    @Size(max = 50, message = "Max size 50")
    private String email;
    @NotNull(message = "Can't be empty")
    private String dateOfBirth;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
