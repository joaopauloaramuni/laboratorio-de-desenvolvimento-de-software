package com.example.GetSubs.dto;

public class DownloadByMovieNameRequestDTO {
    private String movieName;

    // Construtor padrão
    public DownloadByMovieNameRequestDTO() {
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}