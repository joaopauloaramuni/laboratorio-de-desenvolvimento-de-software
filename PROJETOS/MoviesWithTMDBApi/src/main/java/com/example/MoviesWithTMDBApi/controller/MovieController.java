package com.example.MoviesWithTMDBApi.controller;

import com.example.MoviesWithTMDBApi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/now-playing")
    public String getNowPlayingMovies() throws Exception {
        return movieService.getNowPlayingMovies();
    }

    @GetMapping("/popular")
    public String getPopularMovies() throws Exception {
        return movieService.getPopularMovies();
    }

    @GetMapping("/top-rated-movies")
    public String getTopRatedMovies() throws Exception {
        return movieService.getTopRatedMovies();
    }

    @GetMapping("/upcoming")
    public String getUpcomingMovies() throws Exception {
        return movieService.getUpcomingMovies();
    }

    @GetMapping("/horrorMoviesByYear")
    public String getHorrorMoviesByYear(@RequestParam String year) throws Exception {
        return movieService.getHorrorMoviesByYear(year);
    }
    @GetMapping("/tvShowsByYear")
    public String gettvShowsByYear(@RequestParam String year) throws Exception {
        return movieService.gettvShowsByYear(year);
    }
}
