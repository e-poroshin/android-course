package com.gmail.task04_database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;
    private LiveData<List<Contact>> mAllContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insert(Contact contact) {
        mRepository.insert(contact);
    }

    public void update(Contact contact) {
        mRepository.update(contact);
    }

    public void delete(Contact contact) {
        mRepository.delete(contact);
    }
}
