package com.gmail.task02_view;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;


public class CustomViewActivity extends AppCompatActivity implements View.OnTouchListener {

    private DrawCustomView drawCustomView;
    private Toolbar myToolbar;
    private Switch mySwitch;
    private int x, y;
    private String eventDown;
    private int width, height;
    private float centerX, centerY;
    private int minSide;
    private float radius;
    private float radiusMin;
    private Bitmap bitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        drawCustomView = findViewById(R.id.custom_view);
        drawCustomView.setOnTouchListener(this);

        myToolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Custom View");
            myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }

        mySwitch = findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mySwitch.setText("Use Toast");
                } else {
                    mySwitch.setText("Use Snackbar");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        width = v.getMeasuredWidth();
        height = v.getMeasuredHeight();

        centerX = width / 2;
        centerY = height / 2;

        minSide = Math.min(width, height);
        radius = minSide / 3;
        radiusMin = radius / 3;

        x = (int)event.getX();
        y = (int)event.getY();

        if (radius < (float)(Math.sqrt((x-centerX)*(x - centerX) + (y-centerY)*(y-centerY)))) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && mySwitch.isChecked()) {
                eventDown = "Coordinates [x: " + x + "; y: " + y + "]";
                Toast.makeText(v.getContext(), eventDown, Toast.LENGTH_SHORT).show();
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN && !mySwitch.isChecked()) {
                eventDown = "Coordinates [x: " + x + "; y: " + y + "]";
                Snackbar.make(v, eventDown, Snackbar.LENGTH_SHORT).show();
            }
        } else if (radiusMin > (float)(Math.sqrt((x-centerX)*(x - centerX) + (y-centerY)*(y-centerY)))) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                drawCustomView.shuffleColors();
            }
        } else if (radiusMin < (float)(Math.sqrt((x-centerX)*(x - centerX) + (y-centerY)*(y-centerY))) && radius > (float)(Math.sqrt((x-centerX)*(x - centerX) + (y-centerY)*(y-centerY)))) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                int pixel = bitmap.getPixel(x, y);

//                int redValue = Color.red(pixel);
//                int greenValue = Color.green(pixel);
//                int blueValue = Color.blue(pixel);
//                int alphaValue = Color.alpha(pixel);
//
//                int colorValue = Color.rgb(redValue, greenValue, blueValue);
//                if (colorValue == Color.YELLOW) {
//                    Toast.makeText(v.getContext(), "Yellow", Toast.LENGTH_SHORT).show();
//                }
            }
        }
        return true;
    }
}

