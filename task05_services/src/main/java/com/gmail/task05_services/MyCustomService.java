package com.gmail.task05_services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyCustomService extends Service {

    private final String FILENAME = "file_logs";
    private final String LOG_TAG = "my_log";
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        readFile();
        String action = intent.getStringExtra("ACTION");
        String dateAndTimeFormated = getCurrentDateAndTime();
        writeToFile("[" + dateAndTimeFormated + "] " + action);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Файл прочитан");
    }

    public void writeToFile(String action) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_APPEND)));
            bw.write(action + "\n");
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

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "MyCustomService - onDestroy");
        super.onDestroy();
    }
}
