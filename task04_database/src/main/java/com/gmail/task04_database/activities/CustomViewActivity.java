package com.gmail.task04_database.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gmail.task04_database.activities.custom_view.DrawCustomView;
import com.gmail.task04_database.R;
import com.gmail.task04_database.activities.custom_view.onCustomViewTouchListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CustomViewActivity extends AppCompatActivity implements onCustomViewTouchListener {

    private DrawCustomView drawCustomView;
    private Toolbar toolbar;
    private Switch mySwitch;

    private final String LOG_TAG = "myLogs";
    private final String FILENAME = "file";
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String dateAndTimeFormated;


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
        dateAndTimeFormated = getCurrentDateAndTime();
        if (mySwitch.isChecked()) {
            Toast.makeText(this, coordinates, Toast.LENGTH_SHORT).show();
        } else if (!mySwitch.isChecked()) {
            Snackbar.make(drawCustomView, coordinates + " " + dateAndTimeFormated, Snackbar.LENGTH_SHORT).show();
            writeToFile(coordinates);
        }
        return true;
    }

    @Override
    public boolean getColors(String color) {
        dateAndTimeFormated = getCurrentDateAndTime();
        if (mySwitch.isChecked()) {
            Toast.makeText(this, color, Toast.LENGTH_SHORT).show();
        } else if (!mySwitch.isChecked()) {
            Snackbar.make(drawCustomView, color + " " + dateAndTimeFormated, Snackbar.LENGTH_SHORT).show();
            writeToFile(color);
        }
        return true;
    }

    public void writeToFile(String message) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_APPEND)));
            bw.write(message);
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        return currentDateAndTime;
    }
}

