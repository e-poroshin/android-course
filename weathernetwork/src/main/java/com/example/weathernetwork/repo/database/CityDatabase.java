package com.example.weathernetwork.repo.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CityEntity.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static volatile CityDatabase instance;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static CityDatabase getInstance(final Application application) {
        if (instance == null) {
            synchronized (CityDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application,
                            CityDatabase.class,
                            "db_cities.db")
                            .build();
                }
            }
        }
        return instance;
    }
}
