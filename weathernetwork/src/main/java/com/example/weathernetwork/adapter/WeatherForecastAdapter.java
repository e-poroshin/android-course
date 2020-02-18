package com.example.weathernetwork.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathernetwork.R;
import com.example.weathernetwork.weather.Consts;
import com.example.weathernetwork.weather.WeatherForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.RecyclerViewHolder> {

    private List<WeatherForecast> forecastList;

    public WeatherForecastAdapter(List<WeatherForecast> forecast) {
        this.forecastList = new ArrayList<>(forecast);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.textViewForecastNumberOfDay.setText(String.valueOf(forecastList.get(position).getNumberOfDay()));
        holder.textViewForecastDay.setText("day");
        String imageUrl = String.format(Consts.GET_ICON, forecastList.get(position).getIconId());
        Picasso.get().load(imageUrl).into(holder.forecastWeatherIcon);
        holder.textViewForecastTemperature.setText(String.valueOf(forecastList.get(position).getTemperature()));
        holder.textViewForecastDescription.setText(forecastList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (forecastList != null) {
            return forecastList.size();
        } else
            return 0;
    }

    public void setForecast(List<WeatherForecast> forecast) {
        forecastList = forecast;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewForecastNumberOfDay;
        private TextView textViewForecastDay;
        private ImageView forecastWeatherIcon;
        private TextView textViewForecastTemperature;
        private TextView textViewForecastDescription;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForecastNumberOfDay = itemView.findViewById(R.id.itemTextForecastNumberOfDay);
            textViewForecastDay = itemView.findViewById(R.id.itemTextForecastDay);
            forecastWeatherIcon = itemView.findViewById(R.id.itemForecastWeatherIcon);
            textViewForecastTemperature = itemView.findViewById(R.id.itemTextForecastTemperature);
            textViewForecastDescription = itemView.findViewById(R.id.itemTextForecastDescription);
        }
    }
}
