package com.example.finalproject_savemoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject_savemoney.accounts.AccountsViewModel;
import com.example.finalproject_savemoney.categories.CategoryViewModel;
import com.example.finalproject_savemoney.operations.OperationType;
import com.example.finalproject_savemoney.repo.database.AccountEntity;
import com.example.finalproject_savemoney.repo.database.CategoryEntity;
import com.example.finalproject_savemoney.repo.database.OperationEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AddOperationActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.finalproject_savemoney.REPLY";

    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private EditText editTextSum;
    private EditText editTextDescription;
    private OperationEntity operationEntity;
    private OperationType type;
    private Spinner spinnerCategories;
    private Spinner spinnerAccounts;
    private ArrayAdapter<String> adapterCategories;
    private ArrayAdapter<String> adapterAccounts;
    private List<String> categoryNames = new ArrayList<>();
    private List<String> accountNames = new ArrayList<>();
    private CategoryViewModel viewModelCategory;
    private AccountsViewModel viewModelAccount;
    private List<CategoryEntity> categories = new ArrayList<>();
    private List<AccountEntity> accounts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operation);

        toolbar = findViewById(R.id.toolbar_add_operation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        radioGroup = findViewById(R.id.radioGroup);
        editTextSum = findViewById(R.id.editTextSum);
        editTextDescription = findViewById(R.id.editTextDescription);

        type = OperationType.CONSUMPTION;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.radioButtonConsumption) {
                    toolbar.setTitle("Расход");
                    type = OperationType.CONSUMPTION;
                }
                if (group.getCheckedRadioButtonId() == R.id.radioButtonIncome) {
                    toolbar.setTitle("Доход");
                    type = OperationType.INCOME;
                }
            }
        });
        spinnerCategories = findViewById(R.id.spinnerCategories);
        spinnerAccounts = findViewById(R.id.spinnerAccounts);

        viewModelCategory = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModelCategory.getLiveDataCategoryNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                categoryNames = categories;
                Log.d("MY_TAG", categoryNames.toString());
                adapterCategories = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, categoryNames);
                adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategories.setAdapter(adapterCategories);
                spinnerCategories.setSelection(0);
            }
        });
        viewModelCategory.getLiveDataCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categoryEntityList) {
                categories = categoryEntityList;
            }
        });

        viewModelAccount = new ViewModelProvider(this).get(AccountsViewModel.class);
        viewModelAccount.getLiveDataAccountNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> accounts) {
                accountNames = accounts;
                Log.d("MY_TAG", accountNames.toString());
                adapterAccounts = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, accountNames);
                adapterAccounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAccounts.setAdapter(adapterAccounts);
                spinnerAccounts.setSelection(0);
            }
        });
        viewModelAccount.getLiveDataAccounts().observe(this, new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accountEntityList) {
                accounts = accountEntityList;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_operation_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check) {

            int categoryId = categories.get(spinnerCategories.getSelectedItemPosition()).getId();
            int accountId = accounts.get(spinnerAccounts.getSelectedItemPosition()).getId();
            long date = System.currentTimeMillis();
            double sum;
            if (editTextSum.getText().toString().isEmpty()) {
                sum = 0.0;
            } else {
                sum = Double.parseDouble(editTextSum.getText().toString());
            }
            String description;
            if (editTextDescription.getText().toString().isEmpty()) {
                description = "";
            } else {
                description = editTextDescription.getText().toString();
            }

            operationEntity = new OperationEntity(categoryId, accountId, date, sum, description, type);

            Intent replyIntent = new Intent();

            if (TextUtils.isEmpty(editTextSum.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(EXTRA_REPLY, (Serializable) operationEntity);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
