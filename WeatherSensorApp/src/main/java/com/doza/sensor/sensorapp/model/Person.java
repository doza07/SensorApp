package com.doza.sensor.sensorapp.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

@Data
@Entity(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    @NotNull(message = "Can't be empty")
    @Size(max = 30, message = "Max size 30")
    private String username;
    @Column(name = "password")
    @NotNull(message = "Can't be empty")
    @Size(max = 100, message = "Max size 100")
    private String password;
    @Column(name = "email")
    @Email
    @NotNull(message = "Can't be empty")
    @Size(max = 50, message = "Max size 50")
    private String email;
    @Column(name = "date_of_birth")
    @NotNull(message = "Can't be empty")
    private String dateOfBirth;
}
