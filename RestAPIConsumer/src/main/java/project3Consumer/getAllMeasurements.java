package project3Consumer;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class getAllMeasurements {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        List<Double> temperatures = new ArrayList<>();
        String url = "http://localhost:8080/measurements";
        List<HashMap<String,Object>> list = restTemplate.getForObject(url, ArrayList.class);

        List<Double> xData = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            temperatures.add((double)list.get(i).get("value"));
            xData.add((double)i);
        }
        System.out.println(Arrays.toString(temperatures.toArray()));

        XYChart chart = QuickChart.getChart("Sensor chart", "X", "Y", "y(x)", xData, temperatures);
        new SwingWrapper(chart).displayChart();

    }
}
