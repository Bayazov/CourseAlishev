package project3Consumer;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SensorRegistration {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sensor name:");
        String name = scanner.nextLine();
        Map<String,Object> json = new HashMap<>();
        String url = "http://localhost:8080/sensors/registration";
        json.put("name",name);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(json);
        restTemplate.postForObject(url,request, String.class);

    }
}
