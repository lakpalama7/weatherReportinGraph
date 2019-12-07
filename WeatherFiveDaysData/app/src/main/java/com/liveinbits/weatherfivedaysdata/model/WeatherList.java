package com.liveinbits.weatherfivedaysdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherList {
    @SerializedName("dt")
    private Long dt;
    @SerializedName("dt_txt")
    private String datetime;

    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("rain")
    private Rain rain;


    public Long getDt() {
        return dt;
    }

    public String getDatetime() {
        return datetime;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }
}
