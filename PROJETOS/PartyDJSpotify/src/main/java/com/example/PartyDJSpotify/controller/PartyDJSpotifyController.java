package com.example.PartyDJSpotify.controller;

import com.example.PartyDJSpotify.service.PartyDJSpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/party")
public class PartyDJSpotifyController {

    @Autowired
    private PartyDJSpotifyService partyDJSpotifyService;

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
