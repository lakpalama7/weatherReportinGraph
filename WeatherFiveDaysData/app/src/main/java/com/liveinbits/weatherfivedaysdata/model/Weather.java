package com.liveinbits.weatherfivedaysdata.model;

import android.widget.ScrollView;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    private String current_weather;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;

    public Weather(){}

    public Weather(String current_weather, String description, String icon) {
        this.current_weather = current_weather;
        this.description = description;
        this.icon = icon;
    }

    public String getCurrent_weather() {
        return current_weather;
    }

    public void setCurrent_weather(String current_weather) {
        this.current_weather = current_weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
