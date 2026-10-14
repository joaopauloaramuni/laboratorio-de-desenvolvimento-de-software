package com.example.WakaTimeAPI.model;

/**
 * Um "sinal de vida" que a extensão do WakaTime manda a cada ~2 min enquanto
 * você digita (GET /users/current/heartbeats). Só os campos usados no
 * "Codando agora": o arquivo (entity), que traz o caminho completo, fica de fora.
 */
public class Heartbeat {

    private String type;
    private double time; // segundos desde 1970 (com fração)
    private String project;
    private String branch;
    private String language;
    private String user_agent_id;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUser_agent_id() {
        return user_agent_id;
    }

    public void setUser_agent_id(String user_agent_id) {
        this.user_agent_id = user_agent_id;
    }
}
