package com.example.RadioBrowserAPI.model;

public class RadioStation {
    private String changeuuid;
    private String stationuuid;
    private String serveruuid;
    private String name;
    private String url;
    private String urlResolved;
    private String homepage;
    private String favicon;
    private String tags;
    private String country;
    private String countrycode;
    private String iso3166_2;
    private String state;
    private String language;
    private String languagecodes;
    private int votes;
    private String lastchangetime;
    private String lastchangetimeIso8601;
    private String codec;
    private int bitrate;
    private int hls;
    private int lastcheckok;
    private String lastchecktime;
    private String lastchecktimeIso8601;
    private String lastcheckoktime;
    private String lastcheckoktimeIso8601;
    private String lastlocalchecktime;
    private String lastlocalchecktimeIso8601;
    private String clicktimestamp;
    private String clicktimestampIso8601;
    private int clickcount;
    private int clicktrend;
    private int sslError;
    private Double geoLat;
    private Double geoLong;
    private boolean hasExtendedInfo;

    // Método construtor padrão

    public RadioStation(){
    }

    // Método construtor com parâmetros

    public RadioStation(String changeuuid, String stationuuid, String serveruuid, String name, String url, String urlResolved, String homepage, String favicon, String tags, String country, String countrycode, String iso3166_2, String state, String language, String languagecodes, int votes, String lastchangetime, String lastchangetimeIso8601, String codec, int bitrate, int hls, int lastcheckok, String lastchecktime, String lastchecktimeIso8601, String lastcheckoktime, String lastcheckoktimeIso8601, String lastlocalchecktime, String lastlocalchecktimeIso8601, String clicktimestamp, String clicktimestampIso8601, int clickcount, int clicktrend, int sslError, Double geoLat, Double geoLong, boolean hasExtendedInfo) {
        this.changeuuid = changeuuid;
        this.stationuuid = stationuuid;
        this.serveruuid = serveruuid;
        this.name = name;
        this.url = url;
        this.urlResolved = urlResolved;
        this.homepage = homepage;
        this.favicon = favicon;
        this.tags = tags;
        this.country = country;
        this.countrycode = countrycode;
        this.iso3166_2 = iso3166_2;
        this.state = state;
        this.language = language;
        this.languagecodes = languagecodes;
        this.votes = votes;
        this.lastchangetime = lastchangetime;
        this.lastchangetimeIso8601 = lastchangetimeIso8601;
        this.codec = codec;
        this.bitrate = bitrate;
        this.hls = hls;
        this.lastcheckok = lastcheckok;
        this.lastchecktime = lastchecktime;
        this.lastchecktimeIso8601 = lastchecktimeIso8601;
        this.lastcheckoktime = lastcheckoktime;
        this.lastcheckoktimeIso8601 = lastcheckoktimeIso8601;
        this.lastlocalchecktime = lastlocalchecktime;
        this.lastlocalchecktimeIso8601 = lastlocalchecktimeIso8601;
        this.clicktimestamp = clicktimestamp;
        this.clicktimestampIso8601 = clicktimestampIso8601;
        this.clickcount = clickcount;
        this.clicktrend = clicktrend;
        this.sslError = sslError;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.hasExtendedInfo = hasExtendedInfo;
    }

    // Getters e setters

    public String getChangeuuid() {
        return changeuuid;
    }

    public void setChangeuuid(String changeuuid) {
        this.changeuuid = changeuuid;
    }

    public String getStationuuid() {
        return stationuuid;
    }

    public void setStationuuid(String stationuuid) {
        this.stationuuid = stationuuid;
    }

    public String getServeruuid() {
        return serveruuid;
    }

    public void setServeruuid(String serveruuid) {
        this.serveruuid = serveruuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlResolved() {
        return urlResolved;
    }

    public void setUrlResolved(String urlResolved) {
        this.urlResolved = urlResolved;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getIso3166_2() {
        return iso3166_2;
    }

    public void setIso3166_2(String iso3166_2) {
        this.iso3166_2 = iso3166_2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguagecodes() {
        return languagecodes;
    }

    public void setLanguagecodes(String languagecodes) {
        this.languagecodes = languagecodes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getLastchangetime() {
        return lastchangetime;
    }

    public void setLastchangetime(String lastchangetime) {
        this.lastchangetime = lastchangetime;
    }

    public String getLastchangetimeIso8601() {
        return lastchangetimeIso8601;
    }

    public void setLastchangetimeIso8601(String lastchangetimeIso8601) {
        this.lastchangetimeIso8601 = lastchangetimeIso8601;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getHls() {
        return hls;
    }

    public void setHls(int hls) {
        this.hls = hls;
    }

    public int getLastcheckok() {
        return lastcheckok;
    }

    public void setLastcheckok(int lastcheckok) {
        this.lastcheckok = lastcheckok;
    }

    public String getLastchecktime() {
        return lastchecktime;
    }

    public void setLastchecktime(String lastchecktime) {
        this.lastchecktime = lastchecktime;
    }

    public String getLastchecktimeIso8601() {
        return lastchecktimeIso8601;
    }

    public void setLastchecktimeIso8601(String lastchecktimeIso8601) {
        this.lastchecktimeIso8601 = lastchecktimeIso8601;
    }

    public String getLastcheckoktime() {
        return lastcheckoktime;
    }

    public void setLastcheckoktime(String lastcheckoktime) {
        this.lastcheckoktime = lastcheckoktime;
    }

    public String getLastcheckoktimeIso8601() {
        return lastcheckoktimeIso8601;
    }

    public void setLastcheckoktimeIso8601(String lastcheckoktimeIso8601) {
        this.lastcheckoktimeIso8601 = lastcheckoktimeIso8601;
    }

    public String getLastlocalchecktime() {
        return lastlocalchecktime;
    }

    public void setLastlocalchecktime(String lastlocalchecktime) {
        this.lastlocalchecktime = lastlocalchecktime;
    }

    public String getLastlocalchecktimeIso8601() {
        return lastlocalchecktimeIso8601;
    }

    public void setLastlocalchecktimeIso8601(String lastlocalchecktimeIso8601) {
        this.lastlocalchecktimeIso8601 = lastlocalchecktimeIso8601;
    }

    public String getClicktimestamp() {
        return clicktimestamp;
    }

    public void setClicktimestamp(String clicktimestamp) {
        this.clicktimestamp = clicktimestamp;
    }

    public String getClicktimestampIso8601() {
        return clicktimestampIso8601;
    }

    public void setClicktimestampIso8601(String clicktimestampIso8601) {
        this.clicktimestampIso8601 = clicktimestampIso8601;
    }

    public int getClickcount() {
        return clickcount;
    }

    public void setClickcount(int clickcount) {
        this.clickcount = clickcount;
    }

    public int getClicktrend() {
        return clicktrend;
    }

    public void setClicktrend(int clicktrend) {
        this.clicktrend = clicktrend;
    }

    public int getSslError() {
        return sslError;
    }

    public void setSslError(int sslError) {
        this.sslError = sslError;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }

    public Double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(Double geoLong) {
        this.geoLong = geoLong;
    }

    public boolean isHasExtendedInfo() {
        return hasExtendedInfo;
    }

    public void setHasExtendedInfo(boolean hasExtendedInfo) {
        this.hasExtendedInfo = hasExtendedInfo;
    }
}

