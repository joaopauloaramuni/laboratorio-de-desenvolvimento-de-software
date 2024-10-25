package com.example.GetSubs.model;

public class File {
    private int fileId; // ID do arquivo
    private int cdNumber; // Número do CD
    private String fileName; // Nome do arquivo

    public File(int fileId, int cdNumber, String fileName) {
        this.fileId = fileId;
        this.cdNumber = cdNumber;
        this.fileName = fileName;
    }

    // Getters e Setters

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getCdNumber() {
        return cdNumber;
    }

    public void setCdNumber(int cdNumber) {
        this.cdNumber = cdNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", cdNumber=" + cdNumber +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
