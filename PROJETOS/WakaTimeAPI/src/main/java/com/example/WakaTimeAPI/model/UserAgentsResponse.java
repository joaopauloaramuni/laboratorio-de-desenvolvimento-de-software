package com.example.WakaTimeAPI.model;

import java.util.List;

/** Resposta de GET /users/current/user_agents */
public class UserAgentsResponse {

    private List<UserAgent> data;

    public List<UserAgent> getData() {
        return data;
    }

    public void setData(List<UserAgent> data) {
        this.data = data;
    }
}
