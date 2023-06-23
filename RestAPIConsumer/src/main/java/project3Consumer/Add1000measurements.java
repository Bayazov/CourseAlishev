package project3Consumer;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Add1000measurements {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1000; i++){
            Map<String,Object> jsonToSend = new HashMap<>();

            Random random = new Random();
            double value = random.nextDouble() * 200 - 100;
            value = Math.round(value * 100.0) / 100.0;
            jsonToSend.put("value",value);


            boolean bool = random.nextBoolean();
            jsonToSend.put("raining",bool);

            Map<String,Object> sensor = new HashMap<>();
            int randomNumber = random.nextInt(5) + 1;
            sensor.put("name","Sensor" + randomNumber);
            jsonToSend.put("sensor",sensor);

            HttpEntity<Map<String,Object>> request = new HttpEntity<>(jsonToSend);
            String url = "http://localhost:8080/measurements/add";
            restTemplate.postForObject(url,request, String.class);
        }


      /*  LinkedHashMap<String,Object> json = new LinkedHashMap<>();

        json.put("value",12);

        json.put("raining",true);

        json.put("sensor",Map.of("name", "Sensor1"));


        System.out.println(json);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(json);
        String url = "http://localhost:8080/measurements/add";
        res.add(restTemplate.postForObject(url, request, String.class));*/

    }
}
