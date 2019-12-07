package com.liveinbits.weatherfivedaysdata.model;

public class City {
    private String name;
    private String country;
    private Long timezone;
    private Long sunrise;
    private Long sunset;
    private Coord coord;

    public City(){}
    public City(String name, String country, Long timezone, Long sunrise, Long sunset, Coord coord) {
        this.name = name;
        this.country = country;
        this.timezone = timezone;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getTimezone() {
        return timezone;
    }

    public void setTimezone(Long timezone) {
        this.timezone = timezone;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
