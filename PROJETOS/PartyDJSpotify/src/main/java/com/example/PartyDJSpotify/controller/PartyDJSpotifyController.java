package com.example.PartyDJSpotify.controller;

import com.example.PartyDJSpotify.context.AuthorizationCodeContext;
import com.example.PartyDJSpotify.service.PartyDJSpotifyService;
import com.example.PartyDJSpotify.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/party")
public class PartyDJSpotifyController {

    @Autowired
    private PartyDJSpotifyService partyDJSpotifyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorizationCodeContext authorizationCodeContext; // Adiciona a injeção de dependência

    @GetMapping("/authInsomnia")
    public String getAuthorizationUrlString() {
        String authorizationUrl = tokenService.getAuthorizationUrl(); // Obtendo a URL de autorização
        System.out.println("Authorization Url: " + authorizationUrl);
        return authorizationUrl;
    }

    @GetMapping("/authRedirect")
    public ResponseEntity<Void> getAuthorizationUrl() {
        String authorizationUrl = tokenService.getAuthorizationUrl(); // Obtendo a URL de autorização
        System.out.println("Authorization Url: " + authorizationUrl);
        return ResponseEntity.status(HttpStatus.FOUND) // 302 - Found
                .location(URI.create(authorizationUrl)) // Redirecionando para a URL de autorização
                .build();
    }

    // Endpoint para o callback do Spotify
    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
        authorizationCodeContext.setAuthorizationCode(code);
        tokenService.renewAccessToken(code); // Chama o serviço para renovar o token
    return ResponseEntity.ok("Authorization successful!" +
            "<br><br>Authorization Url:<br>" + tokenService.getAuthorizationUrl() +
            "<br><br>AccessToken:<br>" + tokenService.ensureAccessTokenIsValid() +
            "<br><br>Code:<br>" + code); // Mensagem de sucesso
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUserProfile() {
        String userProfile = partyDJSpotifyService.getCurrentUserProfile();
        return ResponseEntity.ok(userProfile);
    }

    // Endpoint para obter a faixa atual em reprodução
    @GetMapping("/current-track")
    public String getCurrentTrack() {
        return partyDJSpotifyService.getCurrentPlayingTrack();
    }

    // Endpoint para obter as faixas do álbum
    @GetMapping("/album/tracks")
    public ResponseEntity<String> getAlbumTracks() {
        String tracks = partyDJSpotifyService.getAlbumTracks();
        return ResponseEntity.ok(tracks);
    }

    // Endpoint para obter a lista de faixas na fila
    @GetMapping("/queue")
    public ResponseEntity<String> getQueueTracks() {
        String tracks = partyDJSpotifyService.getQueueTracks();
        return ResponseEntity.ok(tracks);
    }

    // Endpoint para adicionar uma faixa à fila
    @PostMapping("/queue")
    public ResponseEntity<String> addTrackToQueue(@RequestParam String trackUri) {
        String message = partyDJSpotifyService.addTrackToQueue(trackUri);
        return ResponseEntity.ok(message);
    }

    // Endpoint para remover uma faixa da fila
    @PostMapping("/queue/remove")
    public ResponseEntity<String> removeTrackFromQueue() {
        String message = partyDJSpotifyService.removeTrackFromQueue();
        return ResponseEntity.ok(message);
    }

    // Endpoint para votar em uma música
    @PostMapping("/vote")
    public String voteTrack(@RequestParam String trackId) {
        return partyDJSpotifyService.voteTrack(trackId);
    }

    // Endpoint para pular para a próxima faixa
    @PostMapping("/next-track")
    public String skipTrack() {
        return partyDJSpotifyService.skipToNextTrack();
    }

    // Endpoint para parar a reprodução da música
    @PostMapping("/playback/stop")
    public ResponseEntity<String> stopPlayback() {
        String message = partyDJSpotifyService.stopPlayback();
        return ResponseEntity.ok(message);
    }

    // Endpoint para iniciar a reprodução da música
    @PostMapping("/playback/start")
    public ResponseEntity<String> startPlayback() {
        String message = partyDJSpotifyService.startPlayback();
        return ResponseEntity.ok(message);
    }
}
