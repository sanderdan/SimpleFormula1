package com.sanderdanielsson.android.simpleformula1;

/**
 * Created by Sander on 2015-04-06.
 */

public class Driver {

    public Driver(String firstName, String lastName, String number, String nationality ,String wins, String points, String code, String team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.nationality = nationality;
        this.team = team;
        this.code = code;
        this.wins = wins;
        this.points = points;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    private String firstName;
    private String lastName;
    private String number;
    private String nationality;
    private String team;
    private String code;
    private String wins;
    private String points;




}
