package com.doza.sensor.sensorapp.controllers;

import com.doza.sensor.sensorapp.dto.ConditionsDTO;
import com.doza.sensor.sensorapp.dto.ConditionsResponse;
import com.doza.sensor.sensorapp.exceptions.SensorException;
import com.doza.sensor.sensorapp.model.Conditions;
import com.doza.sensor.sensorapp.service.ConditionsService;
import com.doza.sensor.sensorapp.util.ConditionalsErrorResponse;
import com.doza.sensor.sensorapp.util.ConditionsValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


import static com.doza.sensor.sensorapp.util.Error.returnErrorsToClient;

@Controller
@RequestMapping("/conditions")
public class ConditionsController {

    private final ConditionsService conditionsService;
    private final ConditionsValidator conditionsValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public ConditionsController(ConditionsService conditionsService, ConditionsValidator conditionsValidator, ModelMapper modelMapper) {
        this.conditionsService = conditionsService;
        this.conditionsValidator = conditionsValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid ConditionsDTO conditionsDTO,
                                          BindingResult bindingResult) {
        Conditions conditionAdd = convertToConditions(conditionsDTO);

        conditionsValidator.validate(conditionAdd, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        conditionsService.addConditions(conditionAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    private Conditions convertToConditions(ConditionsDTO conditionsDTO) {
        return modelMapper.map(conditionsDTO, Conditions.class);
    }

    @ExceptionHandler
    private ResponseEntity<ConditionalsErrorResponse> handleException(SensorException e) {
        ConditionalsErrorResponse response = new ConditionalsErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
