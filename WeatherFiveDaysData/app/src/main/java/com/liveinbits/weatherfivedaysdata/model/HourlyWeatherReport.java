package com.liveinbits.weatherfivedaysdata.model;

public class HourlyWeatherReport {
    private String hour;
    private String temp;
    private int icon_id;

    public HourlyWeatherReport(String hour, String temp, int icon_id) {
        this.hour = hour;
        this.temp = temp;
        this.icon_id = icon_id;
    }

    public String getHour() {
        return hour;
    }

    public String getTemp() {
        return temp;
    }

    public int getIcon_id() {
        return icon_id;
    }
}
