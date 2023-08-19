package com.doza.client;
import com.doza.client.dto.ConditionsResponse;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.doza.client.dto.ConditionsDTO;

public class DrawChart {

    public static void main(String[] args) {
        List<Double> temparatures = getTemperaturesFromServer();
        drawChart(temparatures);
    }

    private static List<Double> getTemperaturesFromServer() {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/conditions";

        ConditionsResponse jsonResponse = restTemplate.getForObject(url, ConditionsResponse.class);

        if (jsonResponse == null || jsonResponse.getConditionsDTOS() == null)
            return Collections.emptyList();

        return jsonResponse.getConditionsDTOS().stream().map(ConditionsDTO::getTempC)
                .collect(Collectors.toList());
    }

    private static void drawChart(List<Double> temperatures) {
        double[] xData = IntStream.range(0, temperatures.size()).asDoubleStream().toArray();
        double[] yData = temperatures.stream().mapToDouble(x -> x).toArray();

        XYChart chart = QuickChart.getChart("Temperatures", "X", "Y", "temperature",
                xData, yData);

        new SwingWrapper(chart).displayChart();
    }
}
