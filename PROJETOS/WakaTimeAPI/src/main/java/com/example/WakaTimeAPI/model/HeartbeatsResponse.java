package com.example.WakaTimeAPI.model;

import java.util.List;

/** Resposta de GET /users/current/heartbeats?date=AAAA-MM-DD */
public class HeartbeatsResponse {

    private List<Heartbeat> data;

    public List<Heartbeat> getData() {
        return data;
    }

    public void setData(List<Heartbeat> data) {
        this.data = data;
    }
}
