package com.sanderdanielsson.android.simpleformula1;

/**
 * Created by sander on 2015-04-13.
 */
public class Race {

    String raceName;
    String circuitName;
    String locality;
    String country;
    String date;
    String time;
    String wikiUrl;

    public Race(String raceName, String circuitName, String locality, String country, String date, String time, String wikiUrl) {
        this.raceName = raceName;
        this.circuitName = circuitName;
        this.locality = locality;
        this.country = country;
        this.date = date;
        this.time = time;
        this.wikiUrl = wikiUrl;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getCircuitName() {
        return circuitName;
    }

    public void setCircuitName(String circuitName) {
        this.circuitName = circuitName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }
}
