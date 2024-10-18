package com.example.RestAPI.service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service {
    public String preverTempo() {
        String dadosMeteorologicos = "";
        //String apiUrl = "http://apiadvisor.climatempo.com.br/api/v1/weather/locale/6879/current?token=9fe25332679ebce952fdd9f7f9a83c3e";
        String apiUrl = "https://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale/BR?token=9fe25332679ebce952fdd9f7f9a83c3e";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Código de status:" + responseEntity.getStatusCode());
            dadosMeteorologicos = responseEntity.getBody();
            System.out.println(dadosMeteorologicos);
        } else {
            dadosMeteorologicos = "Falha ao obter dados meteorológicos. Código de status: " + responseEntity.getStatusCode();
        }
        return dadosMeteorologicos;
    }
}
