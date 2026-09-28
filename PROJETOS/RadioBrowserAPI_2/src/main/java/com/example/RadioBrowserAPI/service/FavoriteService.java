package com.example.RadioBrowserAPI.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RadioBrowserAPI.model.FavoriteStation;
import com.example.RadioBrowserAPI.repository.FavoriteRepository;

/**
 * Guarda os favoritos no Supabase (PostgreSQL), persistindo entre reinícios da aplicação.
 */
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional(readOnly = true)
    public boolean isFavorite(String stationUuid) {
        return stationUuid != null && favoriteRepository.existsById(stationUuid);
    }

    /**
     * Busca todos os favoritos numa única consulta (evita 1 query por estação na listagem).
     */
    @Transactional(readOnly = true)
    public Set<String> getFavoriteUuids() {
        return new HashSet<>(favoriteRepository.findAllStationUuids());
    }

    @Transactional
    public void toggle(String stationUuid) {
        if (stationUuid == null) return;

        if (favoriteRepository.existsById(stationUuid)) {
            favoriteRepository.deleteById(stationUuid);
        } else {
            favoriteRepository.save(new FavoriteStation(stationUuid));
        }
    }
}
