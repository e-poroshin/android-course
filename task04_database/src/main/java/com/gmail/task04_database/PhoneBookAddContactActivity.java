package com.gmail.task04_database;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.UUID;


public class PhoneBookAddContactActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.gmail.task04_database.contactlistsql.REPLY";

    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private EditText editTextContactName;
    private EditText editTextContactData;
    private Contact contact;
    private ContactSelectedOption option;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_add_contact);

        toolbar = findViewById(R.id.toolbar_phonebook_add);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add contact");
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        radioGroup = findViewById(R.id.radioGroup);
        editTextContactName = findViewById(R.id.editTextName);
        editTextContactData = findViewById(R.id.editTextPhoneOrEmail);

        option = ContactSelectedOption.PHONE;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioId = group.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.radioButtonPhone) {
                    editTextContactData.setHint("Phone number");
                    option = ContactSelectedOption.PHONE;
                }
                if (checkedRadioId == R.id.radioButtonEmail){
                    editTextContactData.setHint("Email");
                    option = ContactSelectedOption.EMAIL;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
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

            String name = editTextContactName.getText().toString();
            String data = editTextContactData.getText().toString();
            String id = UUID.randomUUID().toString();

            contact = new Contact(name, data, option, id);

            Intent replyIntent = new Intent();

            if (TextUtils.isEmpty(editTextContactName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(EXTRA_REPLY, (Serializable) contact);
                setResult(RESULT_OK, replyIntent);
            }
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}