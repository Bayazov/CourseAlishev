import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {
    public static void main(String[] args) {
        System.out.println("Введите текст на русском языке:");
        Scanner scanner = new Scanner(System.in);
        String sentenceToTranslate = scanner.nextLine();

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + "y0_AgAAAABqfMHTAATuwQAAAADj8z76KANK8kfKRO2ccftgROJJLrAm-ts");

        Map<String,String> jsonData = new HashMap<>();
        jsonData.put("folderId", "b1gmfgau4m2o6i0lmov3");
        jsonData.put("texts", "[" + sentenceToTranslate + "]");
        jsonData.put("targetLanguageCode", "en");
        
        HttpEntity<Map<String,String>> request = new HttpEntity<>(jsonData,httpHeaders);

        String response = restTemplate.postForObject(url,request, String.class);
        System.out.println(response);
    }
}
