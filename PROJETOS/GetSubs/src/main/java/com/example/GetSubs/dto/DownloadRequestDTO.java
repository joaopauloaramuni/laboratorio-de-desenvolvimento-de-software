package com.example.GetSubs.dto;

public class DownloadRequestDTO {
    private int fileId;

    // Construtor padrão
    public DownloadRequestDTO() {
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}