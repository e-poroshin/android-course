package com.example.finalproject_savemoney.accounts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalproject_savemoney.repo.Repository;
import com.example.finalproject_savemoney.repo.database.AccountEntity;

import java.util.List;

public class AccountsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<AccountEntity>> allAccountsLiveData;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allAccountsLiveData = repository.getAllAccounts();
    }

    public LiveData<List<AccountEntity>> getLiveData() {
        return allAccountsLiveData;
    }

    public void insert(AccountEntity accounts) {
        repository.insert(accounts);
    }

    public void update(AccountEntity accounts) {
        repository.update(accounts);
    }

    public void delete(AccountEntity accounts) {
        repository.delete(accounts);
    }
}
