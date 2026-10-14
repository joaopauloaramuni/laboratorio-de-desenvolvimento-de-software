package com.example.WakaTimeAPI.model;

import java.util.List;

/**
 * Estatísticas do período já prontas para a página (templates/home.html):
 * só o que a tela usa, com as linguagens filtradas, coloridas e divididas em
 * "as primeiras" + "o resto".
 */
public class StatsView {

    private String range;
    private String source;            // "oauth" (token) ou "public" (perfil público)
    private boolean pending;          // HTTP 202: o WakaTime ainda está calculando
    private long totalSeconds;
    private long dailyAverage;
    private Integer activeDays;
    private String start;             // "2022-09-06T03:00:00Z"
    private String bestDayDate;
    private Long bestDaySeconds;
    private Long todaySeconds;        // null se o status_bar/today falhar

    private List<StatsItem> languages = List.of();       // todas (barra de cima)
    private List<StatsItem> topLanguages = List.of();    // as primeiras (lista)
    private int restCount;                               // "+ N outras"
    private long restSeconds;
    private double restPercent;

    private List<StatsItem> editors = List.of();
    private List<StatsItem> operatingSystems = List.of();
    private List<StatsItem> categories = List.of();
    private List<StatsItem> projects = List.of();

    /** Editor mais usado (null se não houver). */
    public StatsItem getTopEditor() {
        return editors.isEmpty() ? null : editors.get(0);
    }

    /** Linguagem com mais tempo: base para o tamanho das barras. */
    public long getMaxLanguageSeconds() {
        return topLanguages.isEmpty() ? 1 : Math.max(1, topLanguages.get(0).getSeconds());
    }

    // Getters and Setters
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public long getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(long totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public long getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(long dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public Integer getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getBestDayDate() {
        return bestDayDate;
    }

    public void setBestDayDate(String bestDayDate) {
        this.bestDayDate = bestDayDate;
    }

    public Long getBestDaySeconds() {
        return bestDaySeconds;
    }

    public void setBestDaySeconds(Long bestDaySeconds) {
        this.bestDaySeconds = bestDaySeconds;
    }

    public Long getTodaySeconds() {
        return todaySeconds;
    }

    public void setTodaySeconds(Long todaySeconds) {
        this.todaySeconds = todaySeconds;
    }

    public List<StatsItem> getLanguages() {
        return languages;
    }

    public void setLanguages(List<StatsItem> languages) {
        this.languages = languages;
    }

    public List<StatsItem> getTopLanguages() {
        return topLanguages;
    }

    public void setTopLanguages(List<StatsItem> topLanguages) {
        this.topLanguages = topLanguages;
    }

    public int getRestCount() {
        return restCount;
    }

    public void setRestCount(int restCount) {
        this.restCount = restCount;
    }

    public long getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(long restSeconds) {
        this.restSeconds = restSeconds;
    }

    public double getRestPercent() {
        return restPercent;
    }

    public void setRestPercent(double restPercent) {
        this.restPercent = restPercent;
    }

    public List<StatsItem> getEditors() {
        return editors;
    }

    public void setEditors(List<StatsItem> editors) {
        this.editors = editors;
    }

    public List<StatsItem> getOperatingSystems() {
        return operatingSystems;
    }

    public void setOperatingSystems(List<StatsItem> operatingSystems) {
        this.operatingSystems = operatingSystems;
    }

    public List<StatsItem> getCategories() {
        return categories;
    }

    public void setCategories(List<StatsItem> categories) {
        this.categories = categories;
    }

    public List<StatsItem> getProjects() {
        return projects;
    }

    public void setProjects(List<StatsItem> projects) {
        this.projects = projects;
    }
}
