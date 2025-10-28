package com.example.IBGE_Locations.service;

import org.springframework.stereotype.Service;
import com.example.IBGE_Locations.model.Uf;
import com.example.IBGE_Locations.model.Municipio;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

@Service
public class IbgeLocationsService {

    private static final String BASE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Uf> obterUfs() {
        ResponseEntity<List<Uf>> response = restTemplate.exchange(
                BASE_URL + "/estados",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Uf>>() {}
        );
        return response.getBody();
    }

    public List<Municipio> obterMunicipiosPorUf(String ufSigla) {
        ResponseEntity<List<Municipio>> response = restTemplate.exchange(
                BASE_URL + "/estados/" + ufSigla + "/municipios",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Municipio>>() {}
        );
        return response.getBody();
    }
}
