package com.example.finalproject_savemoney.categories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalproject_savemoney.repo.Repository;
import com.example.finalproject_savemoney.repo.database.CategoryEntity;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<CategoryEntity>> allCategoriesLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCategoriesLiveData = repository.getAllCategories();
    }

    public LiveData<List<CategoryEntity>> getLiveData() {
        return allCategoriesLiveData;
    }

    public void insert(CategoryEntity category) {
        repository.insert(category);
    }

    public void update(CategoryEntity category) {
        repository.update(category);
    }

    public void delete(CategoryEntity category) {
        repository.delete(category);
    }
}
