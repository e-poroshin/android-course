package com.example.weathernetwork.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.weathernetwork.repo.database.CityDao;
import com.example.weathernetwork.repo.database.CityDatabase;
import com.example.weathernetwork.repo.database.CityEntity;

import java.util.List;

public class Repository {

    private CityDao cityDao;
    private LiveData<List<CityEntity>> allCities;

    public Repository(Application application) {
        CityDatabase db = CityDatabase.getInstance(application);
        cityDao = db.cityDao();
        allCities = cityDao.getAllCities();
    }

    public LiveData<List<CityEntity>> getAllCities() {
        return allCities;
    }

    public void insert(final CityEntity city) {
        CityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cityDao.insert(city);
            }
        });
    }

    public void delete(final CityEntity city) {
        CityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cityDao.delete(city);
            }
        });
    }

    public void update(final CityEntity city) {
        CityDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cityDao.update(city);
            }
        });
    }
}
