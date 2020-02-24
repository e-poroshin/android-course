package com.gmail.task04_database.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {

    private ContactDAO mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    ContactRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    void insert(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mContactDao.insert(contact);
        });
    }

    void delete(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mContactDao.delete(contact);
        });
    }

    void update(Contact contact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mContactDao.update(contact);
        });
    }
}
