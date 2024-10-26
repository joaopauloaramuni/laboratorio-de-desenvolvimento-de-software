package com.example.PartyDJSpotify.service;

import com.example.PartyDJSpotify.config.SpotifyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PartyDJSpotifyService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SpotifyConfig spotifyConfig;

    @Autowired
    private VoteService voteService;

    @Autowired
    private TokenService tokenService;

    // Método para buscar informações da faixa atual em reprodução
    public String getCurrentPlayingTrack() {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String url = spotifyConfig.getApiUrl() + "/me/player/currently-playing";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Usa o token de acesso
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    // Método para retornar o usuário corrente
    public String getCurrentUserProfile() {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String url = spotifyConfig.getApiUrl() + "/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken); // Usa o token de acesso
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody(); // Retorna o corpo da resposta
    }

    // Método para obter as faixas de um álbum
    public String getAlbumTracks() {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String albumId = spotifyConfig.getAlbumId();
        String url = spotifyConfig.getApiUrl() + "/albums/" + albumId + "/tracks"; // Endpoint para obter as faixas do álbum

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Adiciona o token de acesso ao cabeçalho
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody(); // Retorna o corpo da resposta com as faixas do álbum
        } else {
            throw new RuntimeException("Falha ao recuperar faixas do álbum: " + response.getBody());
        }
    }

    // Método para pular para a próxima faixa
    public String skipToNextTrack() {
        String accessToken = tokenService.ensureAccessTokenIsValid(); // Garante que o token de acesso esteja válido
        String url = spotifyConfig.getApiUrl() + "/me/player/next";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Usa o token de acesso do TokenContext
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "A faixa foi pulada com sucesso!";
            } else {
                throw new RuntimeException("Falha ao pular a faixa: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pular a faixa: " + e.getMessage());
        }
    }

    // Método para votar em uma faixa
    public String voteTrack(String trackId) {
        tokenService.ensureAccessTokenIsValid();

        // Registrar voto na faixa
        return voteService.registerVote(trackId); // Retorna a mensagem de sucesso do voto
    }

    // Método para adicionar uma faixa à fila
    public String addTrackToQueue(String trackUri) {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String url = spotifyConfig.getApiUrl() + "/me/player/queue?uri=" + trackUri; // Endpoint para adicionar faixa à fila
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "Faixa adicionada à fila com sucesso!";
        } else {
            throw new RuntimeException("Falha ao adicionar faixa à fila: " + response.getBody());
        }
    }

    // Método para remover uma faixa da fila (notar que a API do Spotify não permite remover diretamente, mas podemos pular para a próxima)
    public String removeTrackFromQueue() {
        // Para o Spotify, você pode simular a remoção pulando para a próxima faixa
        return skipToNextTrack();
    }

    // Método para obter a lista de faixas na fila
    public String getQueueTracks() {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String url = spotifyConfig.getApiUrl() + "/me/player/queue"; // Endpoint para obter a fila
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody(); // Retorna as faixas na fila
        } else {
            throw new RuntimeException("Falha ao obter faixas na fila: " + response.getBody());
        }
    }

    // Método para parar a reprodução da música
    public String stopPlayback() {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String url = spotifyConfig.getApiUrl() + "/me/player/pause"; // Endpoint para pausar a reprodução
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "Reprodução pausada com sucesso!";
        } else {
            throw new RuntimeException("Falha ao pausar a reprodução: " + response.getBody());
        }
    }

    // Método para iniciar a reprodução da música
    public String startPlayback() {
        String accessToken = tokenService.ensureAccessTokenIsValid();
        String url = spotifyConfig.getApiUrl() + "/me/player/play"; // Endpoint para iniciar a reprodução
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "Reprodução iniciada com sucesso!";
        } else {
            throw new RuntimeException("Falha ao iniciar a reprodução: " + response.getBody());
        }
    }

}
