package com.example.MarvelExplorerRestAPI.model;

public class Storie {
    private int id;
    private String title;
    private String description;
    private String resourceURI;
    private String type;
    private String modified;
    private Thumbnail thumbnail;
    private Comic comics;
    private Serie series;
    private Event events;
    private Character characters;
    private Creator creators;
    private OriginalIssue originalIssue;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
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

    public Serie getSeries() {
        return series;
    }

    public void setSeries(Serie series) {
        this.series = series;
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

    public OriginalIssue getOriginalIssue() {
        return originalIssue;
    }

    public void setOriginalIssue(OriginalIssue originalIssue) {
        this.originalIssue = originalIssue;
    }
}
