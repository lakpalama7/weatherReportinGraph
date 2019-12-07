package com.liveinbits.weatherfivedaysdata.model;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("3h")
    private double rain_value;

    public Rain(double rain_value) {
        this.rain_value = rain_value;
    }

    public double getRain_value() {
        return rain_value;
    }

    public void setRain_value(double rain_value) {
        this.rain_value = rain_value;
    }
}
