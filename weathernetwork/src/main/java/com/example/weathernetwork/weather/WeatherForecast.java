package com.example.weathernetwork.weather;

public class WeatherForecast {

    private int numberOfDay;
    private String iconId;
    private String description;
    private double temperature;

    public WeatherForecast(int numberOfDay, String iconId, String description, double temperature) {
        this.numberOfDay = numberOfDay;
        this.iconId = iconId;
        this.description = description;
        this.temperature = temperature;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public String getIconId() {
        return iconId;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }
}
