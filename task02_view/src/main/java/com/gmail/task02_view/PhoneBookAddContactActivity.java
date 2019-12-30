package com.gmail.task02_view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class PhoneBookAddContactActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private RadioGroup radioGroup;
    private RadioButton radioButtonPhone;
    private RadioButton radioButtonEmail;
    private EditText editTextName;
    private EditText editTextPhoneOrEmail;

    private Contact contact;
    private boolean isEmailSelected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_add_contact);

        myToolbar = findViewById(R.id.toolbar_phonebook_add);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add contact");
            myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonPhone = findViewById(R.id.radioButtonPhone);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneOrEmail = findViewById(R.id.editTextPhoneOrEmail);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //int checkedRadioId = group.getCheckedRadioButtonId();
                if (radioButtonPhone.isChecked()) {
                    editTextPhoneOrEmail.setHint("Phone number");
                    isEmailSelected = false;
                } else if (radioButtonEmail.isChecked()){
                    editTextPhoneOrEmail.setHint("Email");
                    isEmailSelected = true;
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

            String name = editTextName.getText().toString();
            String phoneOrEmail = editTextPhoneOrEmail.getText().toString();

            contact = new Contact();
            contact.setName(name);
            contact.setData(phoneOrEmail);
            contact.setEmailSelected(isEmailSelected);

            Observer.getInstance().notifyContactChanged(contact);

            PhoneBookMainActivity.addItemAdapter(contact);
            finish();
            editTextName.getText().clear();
            editTextPhoneOrEmail.getText().clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
