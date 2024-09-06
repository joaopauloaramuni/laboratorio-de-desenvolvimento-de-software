package com.example.RestAPI.service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service {

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
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas");
    }
    public String consultarModelos(int id) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+id+"/modelos");
    }
    public String consultarAnos(int marca, int modelo) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos");
    }
    public String consultarValor(int marca, int modelo, String ano) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos/"+ano);
    }
}
