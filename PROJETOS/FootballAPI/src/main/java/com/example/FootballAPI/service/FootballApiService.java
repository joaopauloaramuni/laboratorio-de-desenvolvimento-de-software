package com.example.FootballAPI.service;

import com.example.FootballAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class FootballApiService {

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    private static final List<String> DESIRED_LEAGUES = Arrays.asList("Serie A", "Serie B", "Copa Do Brasil", "Mineiro - 1");

    public List<Map<String, Object>> getUpcomingGames() {
        // Obter as ligas do Brasil
        List<Map<String, Object>> leagues = getBrazilianLeagues();

        // Para armazenar os jogos
        List<Map<String, Object>> upcomingGames = new ArrayList<>();

        // Obter os próximos jogos para cada liga desejada
        for (Map<String, Object> league : leagues) {
            int leagueId = (int) league.get("league_id");
            List<Map<String, Object>> fixtures = getUpcomingFixtures(leagueId);
            upcomingGames.addAll(fixtures);
        }

        // Ordena os jogos primeiro pela liga e depois pela data
        upcomingGames.sort(Comparator
                .comparing((Map<String, Object> o) -> DESIRED_LEAGUES.indexOf((String) o.get("league_name")))  // Ordena pela liga
                .thenComparing(o -> (String) o.get("date"), Comparator.nullsFirst(Comparator.naturalOrder()))  // Ordena pela data
        );

        return upcomingGames;
    }

    private List<Map<String, Object>> getBrazilianLeagues() {
        String url = "https://v3.football.api-sports.io/leagues?country=Brazil";

        // Configuração dos headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "v3.football.api-sports.io");
        headers.set("x-rapidapi-key", apiConfig.getApiKey());

        // Inclui os headers na requisição
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Realiza a requisição GET
        Map<String, Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class).getBody();
        List<Map<String, Object>> leagues = (List<Map<String, Object>>) response.get("response");

        // Filtra as ligas desejadas
        List<Map<String, Object>> filteredLeagues = new ArrayList<>();
        for (Map<String, Object> league : leagues) {
            String leagueName = (String) ((Map<String, Object>) league.get("league")).get("name");
            if (DESIRED_LEAGUES.contains(leagueName)) {
                Map<String, Object> leagueData = new HashMap<>();
                leagueData.put("league_id", ((Map<String, Object>) league.get("league")).get("id"));
                leagueData.put("league_name", leagueName);

                // Obtém a temporada mais recente
                List<Map<String, Object>> seasons = (List<Map<String, Object>>) league.get("seasons");
                seasons.sort((s1, s2) -> Integer.compare((int) s2.get("year"), (int) s1.get("year"))); // Ordena por ano decrescente
                leagueData.put("season", seasons.get(0).get("year"));  // Pega o ano da temporada mais recente

                filteredLeagues.add(leagueData);
            }
        }

        return filteredLeagues;
    }

    private List<Map<String, Object>> getUpcomingFixtures(int leagueId) {
        String url = "https://v3.football.api-sports.io/fixtures";

        // Configuração dos headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "v3.football.api-sports.io");
        headers.set("x-rapidapi-key", apiConfig.getApiKey());

        // Parâmetros para obter os próximos jogos
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("league", String.valueOf(leagueId));
        params.add("next", "10");  // Limita aos 10 próximos jogos

        // Converte os parâmetros para uma URL com query string
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(params);
        String finalUrl = builder.toUriString();  // URL com os parâmetros codificados

        // Inclui os headers na requisição
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Realiza a requisição GET
        Map<String, Object> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, Map.class).getBody();

        // Processa os jogos da resposta
        List<Map<String, Object>> fixtures = (List<Map<String, Object>>) response.get("response");

        List<Map<String, Object>> upcomingFixtures = new ArrayList<>();
        for (Map<String, Object> fixture : fixtures) {
            Map<String, Object> fixtureData = new HashMap<>();

            // Adiciona a data do jogo
            fixtureData.put("date", ((Map<String, Object>) fixture.get("fixture")).get("date"));

            // Adiciona as informações do time da casa e visitante
            fixtureData.put("home_team", ((Map<String, Object>) fixture.get("teams")).get("home"));
            fixtureData.put("away_team", ((Map<String, Object>) fixture.get("teams")).get("away"));

            // Adiciona informações da liga (name e logo)
            fixtureData.put("league_name", ((Map<String, Object>) fixture.get("league")).get("name"));
            fixtureData.put("league_logo", ((Map<String, Object>) fixture.get("league")).get("logo"));

            upcomingFixtures.add(fixtureData);
        }

        return upcomingFixtures;
    }


}
