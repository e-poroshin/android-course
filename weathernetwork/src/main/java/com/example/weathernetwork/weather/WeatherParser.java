package com.example.weathernetwork.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherParser {

    private String data;

    public WeatherParser(String data) {
        this.data = data;
    }

    public WeatherCurrent getParsedCurrentWeather() throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONObject jsonMain = jsonObject.getJSONObject("main");
        double temperature = jsonMain.getDouble("temp");

        JSONObject jsonWeather = jsonObject.getJSONArray("weather").getJSONObject(0);

        String description = jsonWeather.getString("description");
        String iconID = jsonWeather.getString("icon");

        return new WeatherCurrent(description, temperature, iconID);
    }

    public List<WeatherForecast> getParsedListForecastWeather() throws JSONException {
        List<WeatherForecast> forecastList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonList = jsonObject.getJSONArray("list");

        for (int i = 0; i < jsonList.length(); i++) {
            int numberOfDay = i + 1;

            JSONObject jsonCurrentForecast = jsonList.getJSONObject(i);
            JSONObject jsonWeather = jsonCurrentForecast.getJSONArray("weather").getJSONObject(0);

            String description = jsonWeather.getString("description");
            String iconID = jsonWeather.getString("icon");

            JSONObject jsonTemp = jsonCurrentForecast.getJSONObject("temp");
            double temperature = jsonTemp.getDouble("day");

            WeatherForecast currentForecast = new WeatherForecast(numberOfDay, iconID, description, temperature);
            forecastList.add(currentForecast);
        }
        return forecastList;
    }
}
