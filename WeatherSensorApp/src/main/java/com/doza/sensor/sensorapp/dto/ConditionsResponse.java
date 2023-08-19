package com.doza.sensor.sensorapp.dto;

import java.util.List;

public class ConditionsResponse {

    private List<ConditionsDTO> conditionsDTOS;

    public ConditionsResponse(List<ConditionsDTO> conditionsDTOS) {
        this.conditionsDTOS = conditionsDTOS;
    }

    public List<ConditionsDTO> getConditionsDTOS() {
        return conditionsDTOS;
    }

    public void setConditionsDTOS(List<ConditionsDTO> conditionsDTOS) {
        this.conditionsDTOS = conditionsDTOS;
    }
}

