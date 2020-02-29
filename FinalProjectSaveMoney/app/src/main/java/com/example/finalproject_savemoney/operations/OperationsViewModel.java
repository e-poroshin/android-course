package com.example.finalproject_savemoney.operations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalproject_savemoney.repo.Repository;
import com.example.finalproject_savemoney.repo.database.AccountEntity;
import com.example.finalproject_savemoney.repo.database.Operation;
import com.example.finalproject_savemoney.repo.database.OperationEntity;

import java.util.List;

public class OperationsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Operation>> allOperationsLiveData;
    private LiveData<List<String>> allCategoryNamesFromOperationsLiveData;

    public OperationsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allOperationsLiveData = repository.getAllOperations();
        allCategoryNamesFromOperationsLiveData = repository.getCategoryNamesFromOperations();
    }

    public LiveData<List<Operation>> getLiveData() {
        return allOperationsLiveData;
    }

    public LiveData<List<String>> getLiveDataCategoryNamesFromOperations() {
        return allCategoryNamesFromOperationsLiveData;
    }

    public void insert(OperationEntity operations) {
        repository.insert(operations);
    }

    public void update(OperationEntity operations) {
        repository.update(operations);
    }

    public void delete(OperationEntity operations) {
        repository.delete(operations);
    }
}
