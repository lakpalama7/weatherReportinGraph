package com.liveinbits.weatherfivedaysdata.model;

public class DailyWeatherReport {
    private String day_name;
    private double temp;
    private int icon_id;


    public DailyWeatherReport(String day_name, double temp, int icon_id) {
        this.day_name = day_name;
        this.temp = temp;
        this.icon_id = icon_id;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }
}
