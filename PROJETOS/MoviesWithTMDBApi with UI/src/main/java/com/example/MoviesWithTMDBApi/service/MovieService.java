package com.example.MoviesWithTMDBApi.service;

import com.example.MoviesWithTMDBApi.config.TMDBConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {
    private final OkHttpClient okHttpClient;
    private final TMDBConfig tmdbConfig;
    private final ObjectMapper objectMapper;

    public MovieService(TMDBConfig tmdbConfig) {
        this.tmdbConfig = tmdbConfig;
        this.okHttpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Método responsável por executar a requisição HTTP
    private String executeRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + tmdbConfig.getAuthorizationHeader())
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    // Método para parsear a resposta JSON e extrair informações dos filmes
    private List<Map<String, Object>> parseMovies(String jsonResponse) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        List<Map<String, Object>> movies = new ArrayList<>();

        for (JsonNode movieNode : rootNode.path("results")) {
            Map<String, Object> movie = objectMapper.convertValue(movieNode, Map.class);
            movies.add(movie);
        }
        return movies;
    }

    // Método para buscar os filmes populares
    public List<Map<String, Object>> getPopularMoviesData() throws IOException {
        List<Map<String, Object>> allMovies = new ArrayList<>();

        // Buscar por 10 páginas
        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "movie/popular?language=pt-BR&page=" + page;
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> movies = parseMovies(jsonResponse);
            allMovies.addAll(movies);  // Adiciona os filmes da página ao resultado final
        }

        return allMovies;
    }

    // Método para buscar filmes que estão em exibição nos cinemas
    public List<Map<String, Object>> getNowPlayingMoviesData() throws IOException {
        List<Map<String, Object>> allMovies = new ArrayList<>();

        // Buscar por 10 páginas
        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "movie/now_playing?language=pt-BR&page=" + page;
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> movies = parseMovies(jsonResponse);
            allMovies.addAll(movies);  // Adiciona os filmes da página ao resultado final
        }

        return allMovies;
    }

    // Método para buscar filmes mais bem avaliados
    public List<Map<String, Object>> getTopRatedMovies() throws IOException {
        List<Map<String, Object>> allMovies = new ArrayList<>();

        // Buscar por 10 páginas
        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "movie/top_rated?language=pt-BR&page=" + page;
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> movies = parseMovies(jsonResponse);
            allMovies.addAll(movies);  // Adiciona os filmes da página ao resultado final
        }

        return allMovies;
    }

    // Método para buscar filmes em estreia
    public List<Map<String, Object>> getUpcomingMovies() throws IOException {
        List<Map<String, Object>> allMovies = new ArrayList<>();

        // Buscar por 10 páginas
        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "movie/upcoming?language=pt-BR&page=" + page;
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> movies = parseMovies(jsonResponse);
            allMovies.addAll(movies);  // Adiciona os filmes da página ao resultado final
        }

        return allMovies;
    }

    // Método para buscar filmes de terror de um ano específico
    public List<Map<String, Object>> getHorrorMoviesByYear(String year) throws IOException {
        List<Map<String, Object>> allMovies = new ArrayList<>();

        // Buscar por 10 páginas
        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "discover/movie?include_adult=false&include_video=false&language=pt-BR&page=" + page + "&release_date.gte=" + year + "-01-01&release_date.lte=" + year + "-12-31&sort_by=popularity.desc&with_genres=27";
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> movies = parseMovies(jsonResponse);
            allMovies.addAll(movies);  // Adiciona os filmes da página ao resultado final
        }

        return allMovies;
    }

    // Método para buscar séries de TV de um ano específico
    public List<Map<String, Object>> getTvShowsByYear(String year) throws IOException {
        List<Map<String, Object>> allMovies = new ArrayList<>();

        // Buscar por 10 páginas
        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "discover/tv?air_date.gte=" + year + "-01-01&air_date.lte=" + year + "-12-31&include_adult=false&include_null_first_air_dates=false&language=pt-BR&page=" + page + "&sort_by=popularity.desc";
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> movies = parseMovies(jsonResponse);
            allMovies.addAll(movies);  // Adiciona os filmes da página ao resultado final
        }

        return allMovies;
    }

    // Método para buscar séries de TV que estão no ar
    public List<Map<String, Object>> getTopRatedTvShows() throws IOException {
        List<Map<String, Object>> allTvShows = new ArrayList<>();

        for (int page = 1; page <= 10; page++) {
            String url = tmdbConfig.getBaseUrl() + "tv/top_rated?language=pt-BR&page=" + page;
            String jsonResponse = executeRequest(url);
            List<Map<String, Object>> tvShows = parseMovies(jsonResponse);

            // Adiciona os filmes da página ao resultado final
            allTvShows.addAll(tvShows);
        }

        return allTvShows;
    }

    // Método para buscar os filmes populares
    /*public List<Map<String, Object>> getPopularMoviesData() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/popular?language=pt-BR&page=1";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/

    // Método para buscar filmes que estão em exibição nos cinemas
    /*public List<Map<String, Object>> getNowPlayingMoviesData() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/now_playing?language=pt-BR&page=1";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/

    // Método para buscar filmes mais bem avaliados
    /*public List<Map<String, Object>> getTopRatedMovies() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/top_rated?language=pt-BR&page=1";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/

    // Método para buscar filmes em estreia
    /*public List<Map<String, Object>> getUpcomingMovies() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/upcoming?language=pt-BR&page=1";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/

    // Método para buscar filmes de terror de um ano específico
    /*public List<Map<String, Object>> getHorrorMoviesByYear(String year) throws IOException {
        String url = tmdbConfig.getBaseUrl() + "discover/movie?include_adult=false&include_video=false&language=pt-BR&page=1&release_date.gte=" + year + "-01-01&release_date.lte=" + year + "-12-31&sort_by=popularity.desc&with_genres=27";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/

    // Método para buscar séries de TV de um ano específico
    /*public List<Map<String, Object>> getTvShowsByYear(String year) throws IOException {
        String url = tmdbConfig.getBaseUrl() + "discover/tv?air_date.gte=" + year + "-01-01&air_date.lte=" + year + "-12-31&include_adult=false&include_null_first_air_dates=false&language=pt-BR&page=1&sort_by=popularity.desc";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/

    // Método para buscar séries de TV que estão no ar
    /*public List<Map<String, Object>> getTopRatedTvShows() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "tv/top_rated?language=pt-BR&page=1";
        String jsonResponse = executeRequest(url);
        return parseMovies(jsonResponse);
    }*/
}
