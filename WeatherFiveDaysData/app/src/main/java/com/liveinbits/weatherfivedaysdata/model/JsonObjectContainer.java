package com.liveinbits.weatherfivedaysdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonObjectContainer {
    @SerializedName("list")
    private List<WeatherList> weatherListList;
    @SerializedName("city")
    private City city;

    public JsonObjectContainer(){}

    public List<WeatherList> getWeatherListList() {
        return weatherListList;
    }

    public void setWeatherListList(List<WeatherList> weatherListList) {
        this.weatherListList = weatherListList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
