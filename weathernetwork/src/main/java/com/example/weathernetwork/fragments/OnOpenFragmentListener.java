package com.example.weathernetwork.fragments;

public interface OnOpenFragmentListener {
    void onOpenForecastFragment();
    void onOpenForecastFragmentByCityName(String newCityName);
    void onOpenCityListFragment();
    void onOpenTempModePreferenceFragment();
}
