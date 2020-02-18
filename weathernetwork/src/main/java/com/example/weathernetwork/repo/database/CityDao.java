package com.example.weathernetwork.repo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CityEntity city);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(CityEntity city);

    @Delete
    void delete(CityEntity city);

    @Query("SELECT * FROM cities")
    LiveData<List<CityEntity>> getAllCities();

}
