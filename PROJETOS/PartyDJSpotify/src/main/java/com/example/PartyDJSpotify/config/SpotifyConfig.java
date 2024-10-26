package com.example.PartyDJSpotify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyConfig {

    @Value("${spotify.api.url}")
    private String apiUrl;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    @Value("${spotify.auth.url}")
    private String authUrl;

    @Value("${spotify.token.url}")
    private String tokenUrl;

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.album.id}")
    private String albumId;

    @Value("${spotify.playlist.id}")
    private String playlistId;

    // Getters
    public String getApiUrl() {
        return apiUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getPlaylistId() {
        return playlistId;
    }
}
