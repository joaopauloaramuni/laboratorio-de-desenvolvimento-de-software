package com.example.PartyDJSpotify.service;

import com.example.PartyDJSpotify.config.SpotifyConfig;
import com.example.PartyDJSpotify.context.AuthorizationCodeContext;
import com.example.PartyDJSpotify.context.TokenContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class TokenService {

    @Autowired
    private TokenContext tokenContext;

    @Autowired
    private AuthorizationCodeContext authorizationCodeContext;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SpotifyConfig spotifyConfig;

    // Método para obter a URL de autorização
    public String getAuthorizationUrl() {
        String scope = "user-read-playback-state user-read-currently-playing user-read-private user-modify-playback-state";
        try {
            String encodedScope = URLEncoder.encode(scope, "UTF-8"); // Codificando o escopo
            return spotifyConfig.getAuthUrl() + "?client_id=" + spotifyConfig.getClientId() +
                    "&response_type=code" +
                    "&redirect_uri=" + URLEncoder.encode(spotifyConfig.getRedirectUri(), "UTF-8") + // Também codifique a redirect_uri
                    "&scope=" + encodedScope; // Usando o escopo codificado
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Erro ao codificar a URL de autorização", e);
        }
    }

    // Método para verificar e renovar o token de acesso
    public String ensureAccessTokenIsValid() {
        if (tokenContext.isTokenExpired()) {
            String code = authorizationCodeContext.getAuthorizationCode();
            renewAccessToken(code);
        }
        String token = tokenContext.getAccessToken();
        System.out.println("Access Token: " + token);
        return token;
    }

    // Método para renovar o token de acesso
    public void renewAccessToken(String code) {
        System.out.println("Code: " + code);
        String url = spotifyConfig.getTokenUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=" + spotifyConfig.getRedirectUri() +
                "&client_id=" + spotifyConfig.getClientId() +
                "&client_secret=" + spotifyConfig.getClientSecret();

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro durante solicitação de token: " + e.getMessage());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            int expiresIn = jsonResponse.getInt("expires_in");
            tokenContext.setAccessToken(jsonResponse.getString("access_token"), expiresIn);
        } else {
            throw new RuntimeException("Falha ao recuperar o token de acesso da API Spotify: " + response.getBody());
        }
    }

}
