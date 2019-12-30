package com.gmail.task02_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Button buttonPhoneBook;
    private Button buttonCustomView;
    private Button buttonWebPage;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        myToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Choose action");

        buttonPhoneBook = findViewById(R.id.buttonPhoneBook);
        buttonCustomView = findViewById(R.id.buttonCustomView);
        buttonWebPage = findViewById(R.id.buttonWebPage);

        buttonPhoneBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneBookActivityIntent = new Intent(MainActivity.this, PhoneBookMainActivity.class);
                startActivity(phoneBookActivityIntent);
            }
        });

        buttonCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customViewActivityIntent = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(customViewActivityIntent);
            }
        });

        buttonWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webPageActivityIntent = new Intent(MainActivity.this, WebPageActivity.class);
                startActivity(webPageActivityIntent);
            }
        });


    }


}
