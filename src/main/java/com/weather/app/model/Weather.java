package com.weather.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="weatherapp")
public class Weather {
    private String date;
    @Id
    private String time;

    private double temperature;
    private String sunriseTime;
    private String sunsetTime;
    private double temperatureHigh;
    private String temperatureHighTime;
    private double temperatureLow;
    private String temperatureLowTime;

    public Weather(){

    }
    public Weather(String date, String time, double temperature, String sunriseTime, String sunsetTime, double temperatureHigh, String temperatureHighTime, double temperatureLow, String temperatureLowTime) {
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.temperatureHigh = temperatureHigh;
        this.temperatureHighTime = temperatureHighTime;
        this.temperatureLow = temperatureLow;
        this.temperatureLowTime = temperatureLowTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public String getTemperatureHighTime() {
        return temperatureHighTime;
    }

    public void setTemperatureHighTime(String temperatureHighTime) {
        this.temperatureHighTime = temperatureHighTime;
    }

    public double getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public String getTemperatureLowTime() {
        return temperatureLowTime;
    }

    public void setTemperatureLowTime(String temperatureLowTime) {
        this.temperatureLowTime = temperatureLowTime;
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
}
