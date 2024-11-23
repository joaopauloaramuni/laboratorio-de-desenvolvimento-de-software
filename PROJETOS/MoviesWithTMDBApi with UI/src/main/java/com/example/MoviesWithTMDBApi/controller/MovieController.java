package com.example.MoviesWithTMDBApi.controller;

import com.example.MoviesWithTMDBApi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Exibe filmes populares
    @GetMapping("/")
    public String showMoviesHome(Model model) throws Exception {
        List<Map<String, Object>> movies = movieService.getPopularMoviesData();
        model.addAttribute("movies", movies);
        return "home";
    }

    // Exibe filmes em exibição atualmente
    @GetMapping("/now-playing")
    public String getNowPlayingMovies(Model model) throws Exception {
        List<Map<String, Object>> movies = movieService.getNowPlayingMoviesData();
        model.addAttribute("movies", movies);
        return "home";
    }

    // Exibe filmes mais bem avaliados
    @GetMapping("/top-rated")
    public String getTopRatedMovies(Model model) throws Exception {
        List<Map<String, Object>> movies = movieService.getTopRatedMovies();
        model.addAttribute("movies", movies);
        return "home";
    }

    // Exibe filmes em estreia
    @GetMapping("/upcoming")
    public String getUpcomingMovies(Model model) throws Exception {
        List<Map<String, Object>> movies = movieService.getUpcomingMovies();
        model.addAttribute("movies", movies);
        return "home";
    }

    // Exibe filmes de terror por ano
    @GetMapping("/horror/{year}")
    public String getHorrorMoviesByYear(@PathVariable String year, Model model) throws Exception {
        List<Map<String, Object>> movies = movieService.getHorrorMoviesByYear(year);
        model.addAttribute("movies", movies);
        return "home";
    }

    // Exibe séries de TV por ano
    @GetMapping("/tv-shows/{year}")
    public String getTvShowsByYear(@PathVariable String year, Model model) throws Exception {
        List<Map<String, Object>> tvShows = movieService.getTvShowsByYear(year);
        model.addAttribute("tvShows", tvShows);
        return "home";
    }

    // Exibe séries de TV que estão no ar
    @GetMapping("/tv-shows/top-rated")
    public String getTopRatedTvShows(Model model) throws Exception {
        List<Map<String, Object>> tvShows = movieService.getTopRatedTvShows();
        model.addAttribute("tvShows", tvShows);
        return "home";
    }
}
