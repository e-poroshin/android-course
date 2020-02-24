package com.gmail.task04_database.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contacts")
    LiveData<List<Contact>> getAllContacts();


    @Query("SELECT * FROM contacts")
    Cursor getCursorAll();

    @Query("DELETE FROM contacts WHERE id = :id")
    int deleteById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProvider(Contact contact);

    @Update
    int updateProvider(Contact contact);
}
