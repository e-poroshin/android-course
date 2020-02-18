package com.example.weathernetwork.weather;

public class Consts {

    public static final String GET_CURRENT_WEATHER_BY_CITY_NAME_METRIC = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    public static final String GET_FORECAST_WEATHER_BY_CITY_NAME_METRIC = "https://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=3&appid=%s";

    public static final String GET_CURRENT_WEATHER_BY_CITY_NAME_IMPERIAL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=imperial";
    public static final String GET_FORECAST_WEATHER_BY_CITY_NAME_IMPERIAL = "https://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=imperial&cnt=3&appid=%s";

    public static final String GET_ICON = "https://openweathermap.org/img/wn/%s@2x.png";

    public Consts() {
    }
}
