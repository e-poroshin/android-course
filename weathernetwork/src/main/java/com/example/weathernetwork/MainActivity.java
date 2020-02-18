package com.example.weathernetwork;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weathernetwork.fragments.ForecastFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new ForecastFragment(), ForecastFragment.class.getSimpleName())
                .commit();
    }
}
