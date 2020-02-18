package com.example.weathernetwork.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.weathernetwork.R;


public class TempModePreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.fragment_preference);
    }

}
