package com.example.GetSubs.model;
import java.util.List;

public class Subtitle {
    private String id; // ID da legenda
    private String language; // Idioma da legenda
    private String subtitleId; // ID da legenda
    private int downloadCount; // Contagem de downloads
    private boolean hearingImpaired; // Indica se é para deficientes auditivos
    private boolean hd; // Indica se é HD
    private double fps; // Frames por segundo
    private String release; // Informações sobre a liberação
    private String comments; // Comentários
    private String url; // URL da legenda
    private List<File> files; // Lista de arquivos associados

    // Construtor
    public Subtitle(String id, String language, String subtitleId, int downloadCount,
                    boolean hearingImpaired, boolean hd, double fps, String release,
                    String comments, String url, List<File> files) {
        this.id = id;
        this.language = language;
        this.subtitleId = subtitleId;
        this.downloadCount = downloadCount;
        this.hearingImpaired = hearingImpaired;
        this.hd = hd;
        this.fps = fps;
        this.release = release;
        this.comments = comments;
        this.url = url;
        this.files = files;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubtitleId() {
        return subtitleId;
    }

    public void setSubtitleId(String subtitleId) {
        this.subtitleId = subtitleId;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public boolean isHearingImpaired() {
        return hearingImpaired;
    }

    public void setHearingImpaired(boolean hearingImpaired) {
        this.hearingImpaired = hearingImpaired;
    }

    public boolean isHd() {
        return hd;
    }

    public void setHd(boolean hd) {
        this.hd = hd;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Subtitle{" +
                "id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", subtitleId='" + subtitleId + '\'' +
                ", downloadCount=" + downloadCount +
                ", hearingImpaired=" + hearingImpaired +
                ", hd=" + hd +
                ", fps=" + fps +
                ", release='" + release + '\'' +
                ", comments='" + comments + '\'' +
                ", url='" + url + '\'' +
                ", files=" + files +
                '}';
    }
}
