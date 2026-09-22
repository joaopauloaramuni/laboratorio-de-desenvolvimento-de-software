package com.example.RadioBrowserAPI.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Guarda os favoritos em memória enquanto a aplicação estiver rodando.
 * Reinicia zerado a cada restart — não persiste em banco/arquivo, como pedido.
 */
@Service
public class FavoriteService {

    private final Set<String> favoriteStationUuids = ConcurrentHashMap.newKeySet();

    public boolean isFavorite(String stationUuid) {
        return stationUuid != null && favoriteStationUuids.contains(stationUuid);
    }

    public void toggle(String stationUuid) {
        if (stationUuid == null) return;

        if (!favoriteStationUuids.add(stationUuid)) {
            favoriteStationUuids.remove(stationUuid);
        }
    }
}