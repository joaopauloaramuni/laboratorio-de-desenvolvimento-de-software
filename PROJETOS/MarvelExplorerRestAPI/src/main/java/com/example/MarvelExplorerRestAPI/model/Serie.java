package com.example.MarvelExplorerRestAPI.model;

import java.util.Date;
import java.util.List;

public class Serie {
    private int id;
    private String title;
    private String description;
    private String resourceURI;
    private List<Url> urls; // Usando a classe Url existente
    private int startYear;
    private int endYear;
    private String rating;
    private Date modified;
    private Thumbnail thumbnail; // Usando a classe Thumbnail existente
    private Comic comics; // Usando a classe Comics existente
    private Storie stories; // Usando a classe Stories existente
    private Event events; // Usando a classe Events existente
    private Character characters; // Usando a classe Characters existente
    private Creator creators; // Usando a classe Creators existente
    private SeriesNext next;
    private SeriesPrevious previous;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Comic getComics() {
        return comics;
    }

    public void setComics(Comic comics) {
        this.comics = comics;
    }

    public Storie getStories() {
        return stories;
    }

    public void setStories(Storie stories) {
        this.stories = stories;
    }

    public Event getEvents() {
        return events;
    }

    public void setEvents(Event events) {
        this.events = events;
    }

    public Character getCharacters() {
        return characters;
    }

    public void setCharacters(Character characters) {
        this.characters = characters;
    }

    public Creator getCreators() {
        return creators;
    }

    public void setCreators(Creator creators) {
        this.creators = creators;
    }

    public SeriesNext getNext() {
        return next;
    }

    public void setNext(SeriesNext next) {
        this.next = next;
    }

    public SeriesPrevious getPrevious() {
        return previous;
    }

    public void setPrevious(SeriesPrevious previous) {
        this.previous = previous;
    }
}

