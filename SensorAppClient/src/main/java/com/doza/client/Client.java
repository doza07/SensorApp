package com.doza.client;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        final String sensorName = "DozaSensor";
        final String sensorLocation = "DozaLocation";

        registerSensor(sensorName, sensorLocation);

        Random random = new Random();


        double maxTemperature = 45.0;
        double maxWind = 60.0;
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            sendConditions(random.nextDouble() * maxTemperature,
                    random.nextDouble() * maxWind,
                    random.nextBoolean(), sensorName);
        }
    }

    private static void registerSensor(String sensorName, String sensorLocation) {
        final String url = "http://localhost:8080/sensor/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("sensorName", sensorName);
        jsonData.put("sensorLocation", sensorLocation);

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void sendConditions(Double tempC, Double windSpeed,
                                       boolean precipitation, String sensorName) {
        final String url = "http://localhost:8080/conditions/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("tempC", tempC);
        jsonData.put("windSpeed", windSpeed);
        jsonData.put("precipitation", precipitation);
        jsonData.put("sensorDTO", Map.of("sensorName", sensorName));

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Conditions successfully send!");
        } catch (HttpClientErrorException e) {
            System.out.println("ERROR!");
            System.out.println(e.getMessage());
        }
    }
}
