package com.example.weathernetwork.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathernetwork.BuildConfig;
import com.example.weathernetwork.R;
import com.example.weathernetwork.adapter.WeatherForecastAdapter;
import com.example.weathernetwork.weather.Consts;
import com.example.weathernetwork.weather.WeatherCurrent;
import com.example.weathernetwork.weather.WeatherForecast;
import com.example.weathernetwork.weather.WeatherParser;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForecastFragment extends Fragment {

    public final String SAVED_CITY = "SAVED_CITY";

    private ImageView viewWeatherIcon;
    private TextView textViewTemperature;
    private TextView textViewDescriptionWeather;
    private TextView textViewCityName;
    private Toolbar toolbar;
    private String cityName;
    private List<WeatherForecast> listWeatherForecast = new ArrayList<>();
    private RecyclerView recyclerView;
    private WeatherForecastAdapter adapter;
    private SharedPreferences sPref;

    private OnOpenFragmentListener onOpenFragmentListener;


    public static ForecastFragment newInstance(String cityName) {
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CITY_NAME", cityName);
        forecastFragment.setArguments(bundle);

        return forecastFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnOpenFragmentListener) {
            onOpenFragmentListener = (OnOpenFragmentListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getSavedCityName().equals("NO_SAVED")) {
            cityName = getSavedCityName();
        }
        if (getArguments() != null) {
            cityName = getArguments().getString("CITY_NAME");
            saveCityName(cityName);
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        viewWeatherIcon = view.findViewById(R.id.viewWeatherIcon);
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        textViewDescriptionWeather = view.findViewById(R.id.textViewDescriptionWeather);
        textViewCityName = view.findViewById(R.id.textViewCityName);
        view.findViewById(R.id.buttonTemperatureMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOpenFragmentListener != null) {
                    onOpenFragmentListener.onOpenTempModePreferenceFragment();
                }
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view_forecast_list);
        adapter = new WeatherForecastAdapter(listWeatherForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean tempMode = sharedPreferences.getBoolean("switch", false);

        if (cityName != null) {
            textViewCityName.setText(cityName);
            getCurrentWeather(cityName, tempMode);
            getForecastWeather(cityName, tempMode);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            if (onOpenFragmentListener != null) {
                onOpenFragmentListener.onOpenCityListFragment();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCurrentWeather(String cityName, boolean tempMode) {
        String apiKey = BuildConfig.API_KEY;
        String url;
        if (!tempMode) {
            url = String.format(Consts.GET_CURRENT_WEATHER_BY_CITY_NAME_METRIC, cityName, apiKey);
        } else {
            url = String.format(Consts.GET_CURRENT_WEATHER_BY_CITY_NAME_IMPERIAL, cityName, apiKey);
        }
        final Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                clearWeatherData();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();
                WeatherParser weatherParser = new WeatherParser(str);
                try {
                    WeatherCurrent weatherCurrent = weatherParser.getParsedCurrentWeather();
                    showCurrentWeather(weatherCurrent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showCurrentWeather(final WeatherCurrent weatherCurrent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String imageUrl = String.format(Consts.GET_ICON, weatherCurrent.getIconId());
                Picasso.get().load(imageUrl).into(viewWeatherIcon);
                textViewDescriptionWeather.setText(weatherCurrent.getDescription());
                textViewTemperature.setText(String.valueOf(weatherCurrent.getTemperature()));
            }
        });
    }

    private void getForecastWeather(String cityName, boolean tempMode) {
        String apiKey = BuildConfig.API_KEY_FORECAST;
        String url;
        if (!tempMode) {
            url = String.format(Consts.GET_FORECAST_WEATHER_BY_CITY_NAME_METRIC, cityName, apiKey);
        } else {
            url = String.format(Consts.GET_FORECAST_WEATHER_BY_CITY_NAME_IMPERIAL, cityName, apiKey);
        }
        final Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();
                WeatherParser weatherParser = new WeatherParser(str);
                try {
                    List<WeatherForecast> listWeatherForecast = weatherParser.getParsedListForecastWeather();
                    showForecastWeather(listWeatherForecast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showForecastWeather(final List<WeatherForecast> listWeatherForecasts) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setForecast(listWeatherForecasts);
            }
        });
    }

    private void clearWeatherData() {
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                viewWeatherIcon.setImageResource(R.drawable.ic_sentiment_dissatisfied_yellow_24dp);
                textViewTemperature.setText("");
                textViewDescriptionWeather.setText("");
                textViewCityName.setText("No connection");
                Toast.makeText(getContext(), "Connection failed, try again please", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveCityName(String cityName) {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(SAVED_CITY, cityName);
        editor.apply();
    }

    private String getSavedCityName() {
        String savedText = null;
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        savedText = sPref.getString(SAVED_CITY, "NO_SAVED");
        return savedText;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onOpenFragmentListener = null;
    }
}
