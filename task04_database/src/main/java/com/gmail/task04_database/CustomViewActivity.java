package com.gmail.task04_database;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;


public class CustomViewActivity extends AppCompatActivity implements onCustomViewTouchListener {

    private DrawCustomView drawCustomView;
    private Toolbar toolbar;
    private Switch mySwitch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        drawCustomView = findViewById(R.id.custom_view);
        drawCustomView.setOnCustomViewTouchListener(this);

        toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Custom View");
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }

        mySwitch = findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mySwitch.setText("Use Toast");
            } else {
                mySwitch.setText("Use Snackbar");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean getCoordinates(String coordinates) {
        if (mySwitch.isChecked()) {
            Toast.makeText(this, coordinates, Toast.LENGTH_SHORT).show();
        } else if (!mySwitch.isChecked()) {
            Snackbar.make(drawCustomView, coordinates, Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean getColors(String color) {
        if (mySwitch.isChecked()) {
            Toast.makeText(this, color, Toast.LENGTH_SHORT).show();
        } else if (!mySwitch.isChecked()) {
            Snackbar.make(drawCustomView, color, Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }
}

