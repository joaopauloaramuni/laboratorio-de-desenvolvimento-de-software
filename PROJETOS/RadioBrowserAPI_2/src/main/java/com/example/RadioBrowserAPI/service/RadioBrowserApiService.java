package com.example.RadioBrowserAPI.service;

import java.net.URI;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.RadioBrowserAPI.config.ApiConfig;
import com.example.RadioBrowserAPI.model.RadioStation;

@Service
public class RadioBrowserApiService {

    private static final Collator PT_BR_COLLATOR = Collator.getInstance(new Locale("pt", "BR"));

    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;

    public RadioBrowserApiService(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.restTemplate = new RestTemplate();
    }

    // ---------- Estações ----------

    public List<RadioStation> listRadioStations(String country, String state) {

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiConfig.getSearchUrl())
            .queryParam("limit", 100000)
            .queryParam("order", "name");

        if (StringUtils.hasText(country)) {
            builder.queryParam("country", country);
        }
        if (StringUtils.hasText(state)) {
            builder.queryParam("state", state);
        }

        URI uri = builder.build().toUri();

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {});

            List<RadioStation> radioStations = extractRadioStations(response.getBody());
            radioStations.sort(Comparator.comparingInt(RadioStation::getVotes).reversed());
            return radioStations;

        } catch (HttpServerErrorException e) {
            if (e.getStatusCode().value() == 502) {
                System.err.println("Erro: Serviço indisponível. Por favor, tente novamente mais tarde.");
            } else {
                System.err.println("Erro: Não foi possível obter as estações de rádio devido a um problema no servidor.");
            }
            return Collections.emptyList();

        } catch (Exception e) {
            System.err.println("Erro: Ocorreu um problema inesperado ao buscar as estações de rádio.");
            return Collections.emptyList();
        }
    }

    // ---------- País e estado: carregados de forma independente, cada um ordenado ----------

    public List<Map<String, Object>> listCountries() {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiConfig.getCountriesUrl())
                .build().toUri();
        return fetchList(uri);
    }

    public List<Map<String, Object>> listStates() {
        // Lista mundial completa de estados, sem depender do país selecionado.
        // limit alto pra não bater no corte que a API costuma aplicar por padrão.
        URI uri = UriComponentsBuilder.fromHttpUrl(apiConfig.getStatesUrl())
                .queryParam("limit", 100000)
                .queryParam("order", "name")
                .build().toUri();
        return fetchList(uri);
    }

    private List<Map<String, Object>> fetchList(URI uri) {
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {});

            List<Map<String, Object>> items = response.getBody();
            if (items == null) return Collections.emptyList();

            items.sort((a, b) -> PT_BR_COLLATOR.compare(
                    String.valueOf(a.get("name")),
                    String.valueOf(b.get("name"))));

            return items;

        } catch (Exception e) {
            System.err.println("Erro ao buscar lista: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ---------- Extração de estações ----------

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
                station.setUrlResolved((String) stationMap.get("url_resolved"));
                station.setHomepage((String) stationMap.get("homepage"));
                station.setFavicon(stationMap.get("favicon") == null
                        || ((String) stationMap.get("favicon")).isEmpty()
                        ? "imgs/aradio.webp"
                        : (String) stationMap.get("favicon"));
                station.setTags((String) stationMap.get("tags"));
                station.setCountry((String) stationMap.get("country"));
                station.setCountrycode((String) stationMap.get("countrycode"));
                station.setIso3166_2((String) stationMap.get("iso_3166_2"));
                station.setState((String) stationMap.get("state"));
                station.setLanguage((String) stationMap.get("language"));
                station.setLanguagecodes((String) stationMap.get("languagecodes"));

                station.setVotes(stationMap.get("votes") != null ? ((Number) stationMap.get("votes")).intValue() : 0);
                station.setLastchangetime((String) stationMap.get("lastchangetime"));
                station.setLastchangetimeIso8601((String) stationMap.get("lastchangetime_iso8601"));
                station.setCodec((String) stationMap.get("codec"));
                station.setBitrate(stationMap.get("bitrate") != null ? ((Number) stationMap.get("bitrate")).intValue() : 0);
                station.setHls(stationMap.get("hls") != null ? ((Number) stationMap.get("hls")).intValue() : 0);
                station.setLastcheckok(stationMap.get("lastcheckok") != null ? ((Number) stationMap.get("lastcheckok")).intValue() : 0);
                station.setLastchecktime((String) stationMap.get("lastchecktime"));
                station.setLastchecktimeIso8601((String) stationMap.get("lastchecktime_iso8601"));
                station.setLastcheckoktime((String) stationMap.get("lastcheckoktime"));
                station.setLastcheckoktimeIso8601((String) stationMap.get("lastcheckoktime_iso8601"));
                station.setLastlocalchecktime((String) stationMap.get("lastlocalchecktime"));
                station.setLastlocalchecktimeIso8601((String) stationMap.get("lastlocalchecktime_iso8601"));
                station.setClicktimestamp((String) stationMap.get("clicktimestamp"));
                station.setClicktimestampIso8601((String) stationMap.get("clicktimestamp_iso8601"));
                station.setClickcount(stationMap.get("clickcount") != null ? ((Number) stationMap.get("clickcount")).intValue() : 0);
                station.setClicktrend(stationMap.get("clicktrend") != null ? ((Number) stationMap.get("clicktrend")).intValue() : 0);
                station.setSslError(stationMap.get("ssl_error") != null ? ((Number) stationMap.get("ssl_error")).intValue() : 0);
                station.setGeoLat(stationMap.get("geo_lat") != null ? ((Number) stationMap.get("geo_lat")).doubleValue() : 0.0);
                station.setGeoLong(stationMap.get("geo_long") != null ? ((Number) stationMap.get("geo_long")).doubleValue() : 0.0);
                station.setHasExtendedInfo(stationMap.get("has_extended_info") != null ? (Boolean) stationMap.get("has_extended_info") : false);

                radioStations.add(station);
            }
        }

        return radioStations;
    }
}