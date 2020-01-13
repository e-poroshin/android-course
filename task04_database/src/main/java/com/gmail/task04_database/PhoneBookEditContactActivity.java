package com.gmail.task04_database;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;


public class PhoneBookEditContactActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "REPLY";

    private Toolbar toolbar;
    private EditText editTextName;
    private EditText editTextPhoneOrEmail;
    private Button buttonRemove;

    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_edit_contact);

        toolbar = findViewById(R.id.toolbar_phonebook_edit);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Edit contact");
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }

        editTextName = findViewById(R.id.editTextName);
        editTextPhoneOrEmail = findViewById(R.id.editTextPhoneOrEmail);
        buttonRemove = findViewById(R.id.buttonRemove);

        Intent receivedIndent = getIntent();
        if (receivedIndent != null) {
            contact = (Contact) receivedIndent.getSerializableExtra(Contact.class.getSimpleName());

            editTextName.setText(contact.getName());
            editTextPhoneOrEmail.setText(contact.getData());

            if (contact.getOption() == ContactSelectedOption.EMAIL) {
                editTextPhoneOrEmail.setHint("Email");
            } else if (contact.getOption() == ContactSelectedOption.PHONE) {
                editTextPhoneOrEmail.setHint("Phone number");
            }
        }

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                replyIntent.putExtra(EXTRA_REPLY, (Serializable) contact);
                setResult(RESULT_FIRST_USER, replyIntent);
                finish();
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

            String name = editTextName.getText().toString();
            String phoneOrEmail = editTextPhoneOrEmail.getText().toString();

            contact.setName(name);
            contact.setData(phoneOrEmail);

            Intent replyIntent = new Intent();

            replyIntent.putExtra(EXTRA_REPLY, (Serializable) contact);
            setResult(RESULT_OK, replyIntent);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
