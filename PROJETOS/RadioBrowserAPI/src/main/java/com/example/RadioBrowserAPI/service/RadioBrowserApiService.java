package com.example.RadioBrowserAPI.service;

import com.example.RadioBrowserAPI.config.ApiConfig;
import com.example.RadioBrowserAPI.model.RadioStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RadioBrowserApiService {

    @Autowired
    private ApiConfig apiConfig;

    public List<RadioStation> listRadioStations() {
        String url = apiConfig.getSearchUrl() + "?country=Brazil&state=Minas Gerais&city=Belo Horizonte";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Tenta fazer a chamada para a API
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.getForEntity(url, (Class<List<Map<String, Object>>>) (Object) List.class);

            // Extrai as estações de rádio da resposta
            List<RadioStation> radioStations = extractRadioStations(response.getBody());

            // Ordena a lista por votos (decrescente)
            radioStations.sort(Comparator.comparingInt(RadioStation::getVotes).reversed());

            return radioStations;

        } catch (HttpServerErrorException e) {
            // Trata erros do servidor (502 Bad Gateway, por exemplo)
            if (e.getStatusCode().value() == 502) {
                System.err.println("Erro: Serviço indisponível. Por favor, tente novamente mais tarde.");
            } else {
                System.err.println("Erro: Não foi possível obter as estações de rádio devido a um problema no servidor.");
            }
            // Retorna uma lista vazia como fallback em caso de erro no servidor
            return Collections.emptyList();

        } catch (Exception e) {
            // Trata outras exceções genéricas
            System.err.println("Erro: Ocorreu um problema inesperado ao buscar as estações de rádio.");
            return Collections.emptyList();
        }
    }

    private List<RadioStation> extractRadioStations(List<Map<String, Object>> stationsData) {
        List<RadioStation> radioStations = new ArrayList<>();

        if (stationsData != null) {
            for (Map<String, Object> stationMap : stationsData) {
                RadioStation station = new RadioStation();

                station.setChangeuuid((String) stationMap.get("changeuuid"));
                station.setStationuuid((String) stationMap.get("stationuuid"));
                station.setServeruuid((String) stationMap.get("serveruuid"));
                station.setName((String) stationMap.get("name"));
                station.setUrl((String) stationMap.get("url"));
                station.setUrlResolved((String) stationMap.get("urlResolved"));
                station.setHomepage((String) stationMap.get("homepage"));
                station.setFavicon(stationMap.get("favicon") == null
                        || ((String) stationMap.get("favicon")).isEmpty()
                        ? "imgs/aradio.webp"
                        : (String) stationMap.get("favicon"));
                station.setTags((String) stationMap.get("tags"));
                station.setCountry((String) stationMap.get("country"));
                station.setCountrycode((String) stationMap.get("countrycode"));
                station.setIso3166_2((String) stationMap.get("iso3166_2"));
                station.setState((String) stationMap.get("state"));
                station.setLanguage((String) stationMap.get("language"));
                station.setLanguagecodes((String) stationMap.get("languagecodes"));

                // Verifique se o valor é null antes de tentar convertê-lo
                station.setVotes(stationMap.get("votes") != null ? ((Number) stationMap.get("votes")).intValue() : 0);
                station.setLastchangetime((String) stationMap.get("lastchangetime"));
                station.setLastchangetimeIso8601((String) stationMap.get("lastchangetimeIso8601"));
                station.setCodec((String) stationMap.get("codec"));
                station.setBitrate(stationMap.get("bitrate") != null ? ((Number) stationMap.get("bitrate")).intValue() : 0);
                station.setHls(stationMap.get("hls") != null ? ((Number) stationMap.get("hls")).intValue() : 0);
                station.setLastcheckok(stationMap.get("lastcheckok") != null ? ((Number) stationMap.get("lastcheckok")).intValue() : 0);
                station.setLastchecktime((String) stationMap.get("lastchecktime"));
                station.setLastchecktimeIso8601((String) stationMap.get("lastchecktimeIso8601"));
                station.setLastcheckoktime((String) stationMap.get("lastcheckoktime"));
                station.setLastcheckoktimeIso8601((String) stationMap.get("lastcheckoktimeIso8601"));
                station.setLastlocalchecktime((String) stationMap.get("lastlocalchecktime"));
                station.setLastlocalchecktimeIso8601((String) stationMap.get("lastlocalchecktimeIso8601"));
                station.setClicktimestamp((String) stationMap.get("clicktimestamp"));
                station.setClicktimestampIso8601((String) stationMap.get("clicktimestampIso8601"));
                station.setClickcount(stationMap.get("clickcount") != null ? ((Number) stationMap.get("clickcount")).intValue() : 0);
                station.setClicktrend(stationMap.get("clicktrend") != null ? ((Number) stationMap.get("clicktrend")).intValue() : 0);
                station.setSslError(stationMap.get("sslError") != null ? ((Number) stationMap.get("sslError")).intValue() : 0);
                station.setGeoLat(stationMap.get("geoLat") != null ? ((Number) stationMap.get("geoLat")).doubleValue() : 0.0);
                station.setGeoLong(stationMap.get("geoLong") != null ? ((Number) stationMap.get("geoLong")).doubleValue() : 0.0);
                station.setHasExtendedInfo(stationMap.get("hasExtendedInfo") != null ? (Boolean) stationMap.get("hasExtendedInfo") : false);

                radioStations.add(station);
            }
        }

        return radioStations;
    }

}
