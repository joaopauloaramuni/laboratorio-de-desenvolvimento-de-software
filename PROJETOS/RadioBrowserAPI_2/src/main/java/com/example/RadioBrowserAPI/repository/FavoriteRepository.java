package com.example.RadioBrowserAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.RadioBrowserAPI.model.FavoriteStation;

public interface FavoriteRepository extends JpaRepository<FavoriteStation, String> {

    @Query("select f.stationUuid from FavoriteStation f")
    List<String> findAllStationUuids();
}
