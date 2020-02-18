package com.example.weathernetwork.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathernetwork.R;
import com.example.weathernetwork.fragments.FragmentCommunicator;
import com.example.weathernetwork.repo.database.CityEntity;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.RecyclerViewHolder> {

    private List<CityEntity> cities;
    private FragmentCommunicator communicator;

    public CityListAdapter(List<CityEntity> cityList, FragmentCommunicator communication) {
        this.cities = new ArrayList<>(cityList);
        this.communicator = communication;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new RecyclerViewHolder(view, communicator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.ic_location_on_red_24dp);
        holder.textView.setText(cities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (cities != null) {
            return cities.size();
        } else
            return 0;
    }

    public void setCities(List<CityEntity> cityList) {
        cities = cityList;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private FragmentCommunicator mCommunication;

        public RecyclerViewHolder(@NonNull View itemView, FragmentCommunicator mCommunicator) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImageView);
            textView = itemView.findViewById(R.id.itemTextView);
            mCommunication = mCommunicator;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cityName = textView.getText().toString();
                    mCommunication.onItemClickListener(cityName);
                }
            });
        }
    }

}
