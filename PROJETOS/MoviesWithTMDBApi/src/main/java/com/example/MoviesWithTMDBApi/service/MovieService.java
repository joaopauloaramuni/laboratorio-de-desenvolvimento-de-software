package com.example.MoviesWithTMDBApi.service;

import com.example.MoviesWithTMDBApi.config.TMDBConfig;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Service
public class MovieService {
    private final OkHttpClient okHttpClient;
    private final TMDBConfig tmdbConfig;

    public MovieService(TMDBConfig tmdbConfig) {
        this.tmdbConfig = tmdbConfig;
        this.okHttpClient = new OkHttpClient();
    }

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

    public String getPopularMovies() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/popular?language=pt-BR&page=1";
        return executeRequest(url);
    }

    public String getTopRatedMovies() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/top_rated?language=pt-BR&page=1";
        return executeRequest(url);
    }

    public String getUpcomingMovies() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/upcoming?language=pt-BR&page=1";
        return executeRequest(url);
    }

    public String getNowPlayingMovies() throws IOException {
        String url = tmdbConfig.getBaseUrl() + "movie/now_playing?language=pt-BR&page=1";
        return executeRequest(url);
    }

    public String getHorrorMoviesByYear(String year) throws IOException {
        String url = tmdbConfig.getBaseUrl() + "discover/movie?include_adult=false&include_video=false&language=pt-BR&page=1&release_date.gte=" + year + "-01-01&release_date.lte=" + year + "-12-31&sort_by=popularity.desc&with_genres=27";
        return executeRequest(url);
    }

    public String gettvShowsByYear(String year) throws IOException {
        String url = tmdbConfig.getBaseUrl() + "discover/tv?air_date.gte=" + year + "-01-01&air_date.lte=" + year + "-12-31&include_adult=false&include_null_first_air_dates=false&language=pt-BR&page=1&sort_by=popularity.desc";
        return executeRequest(url);
    }
}
