package com.gmail.task05_services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "my_log";
    private final String FILENAME = "file_logs";

    public final static String MY_BROADCAST_CONTEXT_ACTION = "com.gmail.task05_services.ACTION_MY_ACTION";
    public final static String MY_BROADCAST_MANIFEST_ACTION = "com.gmail.task05_services.ACTION_MANIFEST_ACTION";

    Button buttonContextAction;
    Button buttonManifestAction;

    BroadcastReceiver receiverContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonContextAction = findViewById(R.id.buttonStartContextService);
        buttonManifestAction = findViewById(R.id.buttonStartManifestService);

        receiverContext = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
                    Toast.makeText(context,"Airplane mode changed", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(context, MyCustomService.class);
                    intent1.putExtra("ACTION", action);
                    context.startService(intent1);
                    context.stopService(intent1);
                    readFile();
                }
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Toast.makeText(context, "Screen on", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(context, MyCustomService.class);
                    intent1.putExtra("ACTION", action);
                    context.startService(intent1);
                    context.stopService(intent1);
                    readFile();
                }

                if (MY_BROADCAST_CONTEXT_ACTION.equals(action)) {
                    String text = intent.getStringExtra("TEXT");
                    Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(context, MyCustomService.class);
                    intent1.putExtra("ACTION", action);
                    context.startService(intent1);
                    context.stopService(intent1);
                }
            }
        };

        IntentFilter intentFilterContext = new IntentFilter();
        intentFilterContext.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilterContext.addAction(Intent.ACTION_SCREEN_ON);
        intentFilterContext.addAction(MY_BROADCAST_CONTEXT_ACTION);
        getApplicationContext().registerReceiver(receiverContext, intentFilterContext);

        buttonContextAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MY_BROADCAST_CONTEXT_ACTION);
                intent.putExtra("TEXT", "My custom context action");
                sendBroadcast(intent);
                readFile();
            }
        });

        buttonManifestAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReceiverManifest.class);
                intent.setAction(MY_BROADCAST_MANIFEST_ACTION);
                intent.putExtra("TEXT", "This is my custom manifest action");
                sendBroadcast(intent);
                readFile();
            }
        });

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

    @Override
    protected void onDestroy() {
        getApplicationContext().unregisterReceiver(receiverContext);
        super.onDestroy();
    }

}
