package com.example.weathernetwork.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weathernetwork.repo.Repository;
import com.example.weathernetwork.repo.database.CityEntity;

import java.util.List;

public class CityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<CityEntity>> allCities;

    public CityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCities = repository.getAllCities();
    }

    public LiveData<List<CityEntity>> getLiveData() {
        return allCities;
    }

    public void insert(CityEntity city) {
        repository.insert(city);
    }

    public void update(CityEntity city) {
        repository.update(city);
    }

    public void delete(CityEntity city) {
        repository.delete(city);
    }
}
