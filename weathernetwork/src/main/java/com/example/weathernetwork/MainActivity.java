package com.example.weathernetwork;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weathernetwork.fragments.CityListFragment;
import com.example.weathernetwork.fragments.ForecastFragment;
import com.example.weathernetwork.fragments.OnOpenFragmentListener;
import com.example.weathernetwork.fragments.TempModePreferenceFragment;


public class MainActivity extends AppCompatActivity implements OnOpenFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onOpenForecastFragment();
    }

    @Override
    public void onOpenForecastFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new ForecastFragment(), ForecastFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onOpenForecastFragmentByCityName(String newCityName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, ForecastFragment.newInstance(newCityName), ForecastFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onOpenCityListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, CityListFragment.newInstance(), CityListFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onOpenTempModePreferenceFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new TempModePreferenceFragment(), TempModePreferenceFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }
}
