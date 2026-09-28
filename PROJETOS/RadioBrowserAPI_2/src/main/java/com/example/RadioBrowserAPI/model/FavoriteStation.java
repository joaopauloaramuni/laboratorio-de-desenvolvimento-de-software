package com.example.RadioBrowserAPI.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * Estação favorita persistida na tabela "favorites" do Supabase.
 */
@Entity
@Table(name = "favorites")
public class FavoriteStation {

    @Id
    @Column(name = "station_uuid", nullable = false, length = 64)
    private String stationUuid;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public FavoriteStation() {
    }

    public FavoriteStation(String stationUuid) {
        this.stationUuid = stationUuid;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    public String getStationUuid() {
        return stationUuid;
    }

    public void setStationUuid(String stationUuid) {
        this.stationUuid = stationUuid;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
