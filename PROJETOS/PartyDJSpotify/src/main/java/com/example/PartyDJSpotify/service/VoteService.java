package com.example.PartyDJSpotify.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoteService {
    private final Map<String, Integer> voteCounts = new HashMap<>();

    // Método para registrar o voto em uma faixa
    public String registerVote(String trackId) {
        // Verifica se a faixa já tem votos registrados
        voteCounts.put(trackId, voteCounts.getOrDefault(trackId, 0) + 1);
        return "Voto registrado para a faixa: " + trackId + ". Total de votos: " + voteCounts.get(trackId);
    }

    // Método para obter os votos de uma faixa específica
    public int getVoteCount(String trackId) {
        return voteCounts.getOrDefault(trackId, 0);
    }

    // Método para listar todas as faixas e seus votos
    public List<String> listVotes() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            result.add("Faixa ID: " + entry.getKey() + ", Votos: " + entry.getValue());
        }
        return result;
    }
}
