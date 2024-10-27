package com.example.MarvelExplorerRestAPI.controller;

import com.example.MarvelExplorerRestAPI.model.*;
import com.example.MarvelExplorerRestAPI.model.Character;
import com.example.MarvelExplorerRestAPI.service.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    @Autowired
    private MarvelService marvelService;

    @GetMapping("/characters")
    public List<Character> getCharacters() {
        return marvelService.getCharacters();
    }

    @GetMapping("/comics")
    public List<Comic> getComics() {
        return marvelService.getComics();
    }

    @GetMapping("/events")
    public List<Event> getEvents() {
        return marvelService.getEvents();
    }

    @GetMapping("/series")
    public List<Serie> getSeries() {
        return marvelService.getSeries();
    }

    @GetMapping("/stories")
    public List<Storie> getStories() {
        return marvelService.getStories();
    }

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return marvelService.getCharacterById(id);
    }

    @GetMapping("/comics/{id}")
    public Comic getComicById(@PathVariable String id) {
        return marvelService.getComicById(id);
    }

    @GetMapping("/events/{id}")
    public Event getEventsById(@PathVariable String id) {
        return marvelService.getEventById(id);
    }

    @GetMapping("/series/{id}")
    public Serie getSeriesById(@PathVariable String id) {
        return marvelService.getSerieById(id);
    }

    @GetMapping("/stories/{id}")
    public Storie getStoriesById(@PathVariable String id) {
        return marvelService.getStorieById(id);
    }
}
