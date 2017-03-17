package com.msc.idol.mypa.model.weather;

/**
 * Created by adesai on 3/14/2017.
 */

public class Weather {
    String cityName;
    private Double tempMain, tempMin, tempMax;
    private Double humidity, pressure;

    public Weather(String cityName, Double tempMain, Double tempMin, Double tempMax, Double humidity, Double pressure) {
        this.cityName = cityName;
        this.tempMain = tempMain;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getTempMain() {
        return tempMain;
    }

    public void setTempMain(Double tempMain) {
        this.tempMain = tempMain;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
