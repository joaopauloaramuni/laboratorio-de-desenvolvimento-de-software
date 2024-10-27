package com.example.MarvelExplorerRestAPI.service;

import com.example.MarvelExplorerRestAPI.model.*;
import com.example.MarvelExplorerRestAPI.model.Character;
import com.example.MarvelExplorerRestAPI.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MarvelService {

    @Autowired
    private MarvelApiService marvelApiService;

    @Autowired
    private RestTemplate restTemplate;

    public List<Character> getCharacters() {
        String url = marvelApiService.buildUrl("characters");
        CharactersResponse response = restTemplate.getForObject(url, CharactersResponse.class);
        return response.getData().getResults(); // Retorna a lista de personagens
    }

    public List<Comic> getComics() {
        String url = marvelApiService.buildUrl("comics");
        ComicsResponse response = restTemplate.getForObject(url, ComicsResponse.class);
        return response.getData().getResults(); // Retorna a lista de quadrinhos
    }

    public List<Event> getEvents() {
        String url = marvelApiService.buildUrl("events");
        EventsResponse response = restTemplate.getForObject(url, EventsResponse.class);
        return response.getData().getResults(); // Retorna a lista de eventos
    }

    public List<Serie> getSeries() {
        String url = marvelApiService.buildUrl("series");
        SeriesResponse response = restTemplate.getForObject(url, SeriesResponse.class);
        return response.getData().getResults(); // Retorna a lista de séries
    }

    public List<Storie> getStories() {
        String url = marvelApiService.buildUrl("stories");
        StoriesResponse response = restTemplate.getForObject(url, StoriesResponse.class);
        return response.getData().getResults(); // Retorna a lista de stories
    }

    public Character getCharacterById(String id) {
        String url = marvelApiService.buildUrl("characters/" + id);
        CharactersResponse response = restTemplate.getForObject(url, CharactersResponse.class);
        return response.getData().getResults().isEmpty() ? null : response.getData().getResults().get(0);
    }

    public Comic getComicById(String id) {
        String url = marvelApiService.buildUrl("comics/" + id);
        ComicsResponse response = restTemplate.getForObject(url, ComicsResponse.class);
        return response.getData().getResults().isEmpty() ? null : response.getData().getResults().get(0);
    }

    public Event getEventById(String id) {
        String url = marvelApiService.buildUrl("events/" + id);
        EventsResponse response = restTemplate.getForObject(url, EventsResponse.class);
        return response.getData().getResults().isEmpty() ? null : response.getData().getResults().get(0);
    }

    public Serie getSerieById(String id) {
        String url = marvelApiService.buildUrl("series/" + id);
        SeriesResponse response = restTemplate.getForObject(url, SeriesResponse.class);
        return response.getData().getResults().isEmpty() ? null : response.getData().getResults().get(0);
    }

    public Storie getStorieById(String id) {
        String url = marvelApiService.buildUrl("stories/" + id);
        StoriesResponse response = restTemplate.getForObject(url, StoriesResponse.class);
        return response.getData().getResults().isEmpty() ? null : response.getData().getResults().get(0);
    }

}
