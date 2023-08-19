package com.doza.sensor.sensorapp.controllers;

import com.doza.sensor.sensorapp.dto.SensorDTO;
import com.doza.sensor.sensorapp.exceptions.SensorException;
import com.doza.sensor.sensorapp.exceptions.SensorNotFoundException;
import com.doza.sensor.sensorapp.model.Sensor;
import com.doza.sensor.sensorapp.service.SensorService;
import com.doza.sensor.sensorapp.util.SensorErrorResponse;
import com.doza.sensor.sensorapp.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.doza.sensor.sensorapp.util.Error.returnErrorsToClient;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<SensorDTO> getAllSensor() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public Sensor getSensor(@PathVariable("id") int id) throws SensorNotFoundException {
        return sensorService.findOne(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) throws SensorException {
        Sensor sensorAdd = convertToSensor(sensorDTO);
        sensorValidator.validate(sensorAdd, bindingResult);

        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        sensorService.register(sensorAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException ex) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorException ex) {
        SensorErrorResponse response = new SensorErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

}
