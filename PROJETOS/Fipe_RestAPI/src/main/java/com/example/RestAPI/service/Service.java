package com.example.RestAPI.service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service {

    private static final String BASE_URL = "https://fipe.parallelum.com.br/api/v2";

    // https://fipe.api.br/docs/api/fipe
    private String consultarURL(String apiUrl){
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dados = responseEntity.getBody();
        } else {
            dados = "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
        return dados;
    }
    public String consultarMarcas() {
        // return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas");
        return consultarURL(BASE_URL + "/cars/brands");
    }
    public String consultarModelos(int id) {
        // return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+id+"/modelos");
        return consultarURL(BASE_URL + "/cars/brands/"+id+"/models");
    }
    public String consultarAnos(int marca, int modelo) {
        // return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos");
        return consultarURL(BASE_URL + "/cars/brands/"+marca+"/models/"+modelo+"/years");
    }
    public String consultarValor(int marca, int modelo, String ano) {
        // return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos/"+ano);
        return consultarURL(BASE_URL + "/cars/brands/"+marca+"/models/"+modelo+"/years/"+ano);
    }
}
