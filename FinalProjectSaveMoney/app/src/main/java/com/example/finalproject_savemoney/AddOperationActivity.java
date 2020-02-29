package com.example.finalproject_savemoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject_savemoney.operations.OperationType;
import com.example.finalproject_savemoney.repo.database.Operation;
import com.example.finalproject_savemoney.repo.database.OperationEntity;

import java.io.Serializable;


public class AddOperationActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.finalproject_savemoney.REPLY";

    private final String DATE_FORMAT = "dd-MM-yyyy";
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private EditText editTextSum;
    private EditText editTextDescription;
    private OperationEntity operation;
    private OperationType type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operation);

        toolbar = findViewById(R.id.toolbar_add_operation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.arrow_left);
        }
        radioGroup = findViewById(R.id.radioGroup);
        editTextSum = findViewById(R.id.editTextSum);
        editTextDescription = findViewById(R.id.editTextDescription);

        type = OperationType.CONSUMPTION;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.radioButtonConsumption) {
                    toolbar.setTitle("Добавить операцию - Расход");
                    type = OperationType.CONSUMPTION;
                }
                if (group.getCheckedRadioButtonId() == R.id.radioButtonIncome) {
                    toolbar.setTitle("Добавить операцию - Доход");
                    type = OperationType.INCOME;
                }
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
            String categoryName = null;
            int categoryIcon = 0;
            String accountName = null;
            String accountCurrency = null;
            long date = System.currentTimeMillis();;
            double sum = Double.parseDouble(editTextSum.getText().toString());
            String description = editTextDescription.getText().toString();
//
//            ArrayAdapter
//
////            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
////            long timeMillis = System.currentTimeMillis();
////            Date date = new Date(timeMillis);
////            String currentDate = sdf.format(date);
//            operation = new OperationEntity(categoryID, categoryIcon, accountName, accountCurrency, date, sum, description, type);

            Intent replyIntent = new Intent();

            if (TextUtils.isEmpty(editTextSum.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(EXTRA_REPLY, (Serializable) operation);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
