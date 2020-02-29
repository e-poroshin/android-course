package com.example.finalproject_savemoney.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.finalproject_savemoney.repo.database.AccountDao;
import com.example.finalproject_savemoney.repo.database.AccountEntity;
import com.example.finalproject_savemoney.repo.database.AppDatabase;
import com.example.finalproject_savemoney.repo.database.CategoryDao;
import com.example.finalproject_savemoney.repo.database.CategoryEntity;
import com.example.finalproject_savemoney.repo.database.Operation;
import com.example.finalproject_savemoney.repo.database.OperationDao;
import com.example.finalproject_savemoney.repo.database.OperationEntity;

import java.util.List;

public class Repository {

    private CategoryDao categoryDao;
    private LiveData<List<CategoryEntity>> allCategoriesLiveData;
    private AccountDao accountDao;
    private LiveData<List<AccountEntity>> allAccountsLiveData;
    private OperationDao operationDao;
    private LiveData<List<Operation>> allOperationsLiveData;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        categoryDao = db.categoryDao();
        allCategoriesLiveData = categoryDao.getAllCategories();
        accountDao = db.accountDao();
        allAccountsLiveData = accountDao.getAllAccounts();
        operationDao = db.operationDao();
        allOperationsLiveData = operationDao.getAllOperations();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return allCategoriesLiveData;
    }

    public void insert(final CategoryEntity category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.insert(category);
            }
        });
    }

    public void delete(final CategoryEntity category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.delete(category);
            }
        });
    }

    public void update(final CategoryEntity category) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.update(category);
            }
        });
    }


    public LiveData<List<AccountEntity>> getAllAccounts() {
        return allAccountsLiveData;
    }

    public void insert(final AccountEntity accounts) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                accountDao.insert(accounts);
            }
        });
    }

    public void delete(final AccountEntity accounts) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                accountDao.delete(accounts);
            }
        });
    }

    public void update(final AccountEntity accounts) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                accountDao.update(accounts);
            }
        });
    }


    public LiveData<List<Operation>> getAllOperations() {
        return allOperationsLiveData;
    }

    public void insert(final OperationEntity operations) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                operationDao.insert(operations);
            }
        });
    }

    public void delete(final OperationEntity operations) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                operationDao.delete(operations);
            }
        });
    }

    public void update(final OperationEntity operations) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                operationDao.update(operations);
            }
        });
    }
}
