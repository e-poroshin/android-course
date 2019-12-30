package com.gmail.task02_view;

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


public class PhoneBookEditContactActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private EditText editTextName;
    private EditText editTextPhoneOrEmail;
    private Button buttonRemove;

    private Contact contact;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_edit_contact);

        myToolbar = findViewById(R.id.toolbar_phonebook_edit);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Edit contact");
            myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }

        editTextName = findViewById(R.id.editTextName);
        editTextPhoneOrEmail = findViewById(R.id.editTextPhoneOrEmail);
        buttonRemove = findViewById(R.id.buttonRemove);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            contact = (Contact) arguments.getSerializable(Contact.class.getSimpleName());
            position = arguments.getInt("position");
            contact.setPosition(position);

            editTextName.setText(contact.getName());
            editTextPhoneOrEmail.setText(contact.getData());

            if (contact.isEmailSelected()) {
                editTextPhoneOrEmail.setHint("Email");
            }
        }

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneBookMainActivity.removeItemAdapter(contact);
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

            Observer.getInstance().notifyContactChanged(contact);
            PhoneBookMainActivity.changeItemAdapter(contact);
            finish();

            editTextName.getText().clear();
            editTextPhoneOrEmail.getText().clear();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
