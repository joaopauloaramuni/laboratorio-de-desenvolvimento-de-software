package com.example.WakaTimeAPI.model;

/** Editor/plugin que mandou os heartbeats (GET /users/current/user_agents). */
public class UserAgent {

    private String id;
    private String editor;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
