package com.example.weathernetwork.weather;

public class WeatherCurrent {

    private String description;
    private double temperature;
    private String iconId;

    public WeatherCurrent(String description, double temperature, String iconId) {
        this.description = description;
        this.temperature = temperature;
        this.iconId = iconId;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getIconId() {
        return iconId;
    }
}
