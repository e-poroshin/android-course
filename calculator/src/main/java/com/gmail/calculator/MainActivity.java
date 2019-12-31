package com.gmail.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button buttonC;
    private Button buttonBackspace;
    private Button buttonRatio;
    private boolean ratio;
    private Button buttonMultiply;
    private boolean multiply;
    private Button buttonMinus;
    private boolean minus;
    private Button buttonPlus;
    private boolean plus;
    private Button buttonPercent;
    private boolean percent;
    private Button buttonPoint;
    private Button buttonEquals;
    private Button button9;
    private Button button8;
    private Button button7;
    private Button button6;
    private Button button5;
    private Button button4;
    private Button button3;
    private Button button2;
    private Button button1;
    private Button button0;
    private double firstNumber;
    private double secondNumber;
    private double result;
    private String stringResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        buttonC = findViewById(R.id.buttonC);
        buttonRatio = findViewById(R.id.buttonRatio);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonBackspace = findViewById(R.id.buttonBackspace);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonPercent = findViewById(R.id.buttonPercent);
        buttonPoint = findViewById(R.id.buttonPoint);
        buttonEquals = findViewById(R.id.buttonEquals);
        button9 = findViewById(R.id.button9);
        button8 = findViewById(R.id.button8);
        button7 = findViewById(R.id.button7);
        button6 = findViewById(R.id.button6);
        button5 = findViewById(R.id.button5);
        button4 = findViewById(R.id.button4);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);
        button1 = findViewById(R.id.button1);
        button0 = findViewById(R.id.button0);

        buttonC.setOnClickListener(this);
        buttonRatio.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonBackspace.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        button9.setOnClickListener(this);
        button8.setOnClickListener(this);
        button7.setOnClickListener(this);
        button6.setOnClickListener(this);
        button5.setOnClickListener(this);
        button4.setOnClickListener(this);
        button3.setOnClickListener(this);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
        button0.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonC :
                textView2.setText("");
                break;
            case R.id.buttonRatio :
                textView1.setText("/");
                ratio = true;
                firstNumber = Double.valueOf(textView2.getText().toString());
                textView3.setText(textView2.getText().toString());
                textView2.setText("");
                break;
            case R.id.buttonMultiply :
                textView1.setText("*");
                multiply = true;
                firstNumber = Double.valueOf(textView2.getText().toString());
                textView3.setText(textView2.getText().toString());
                textView2.setText("");
                break;
            case R.id.buttonBackspace :
                String str = removeChar(textView2.getText().toString());
                textView2.setText(str);
                break;
            case R.id.buttonMinus :
                textView1.setText("-");
                minus = true;
                firstNumber = Double.valueOf(textView2.getText().toString());
                textView3.setText(textView2.getText().toString());
                textView2.setText("");
                break;
            case R.id.buttonPlus :
                textView1.setText("+");
                plus = true;
                firstNumber = Double.valueOf(textView2.getText().toString());
                textView3.setText(textView2.getText().toString());
                textView2.setText("");
                break;
            case R.id.buttonPercent :
                secondNumber = Double.valueOf(textView2.getText().toString());
                percent = true;
                if (plus) {
                    stringResult = String.valueOf(firstNumber + (firstNumber * secondNumber / 100));
                    plus = false;
                    percent = false;
                } else if (minus) {
                    stringResult = String.valueOf(firstNumber - (firstNumber * secondNumber / 100));
                    minus = false;
                    percent = false;
                } else if (multiply) {
                    stringResult = String.valueOf(firstNumber * secondNumber / 100);
                    multiply = false;
                    percent = false;
                } else if (ratio) {
                    stringResult = String.valueOf(firstNumber / secondNumber * 100);
                    ratio = false;
                    percent = false;
                }
                textView1.setText("");
                textView3.setText("");
                textView2.setText(stringResult);
                break;
            case R.id.buttonPoint :
                textView2.append(".");
                break;
            case R.id.buttonEquals :
                secondNumber = Double.valueOf(textView2.getText().toString());
                if (plus) {
                    result = firstNumber + secondNumber;
                    plus = false;
                } else if (minus) {
                    result = firstNumber - secondNumber;
                    minus = false;
                } else if (multiply) {
                    result = firstNumber * secondNumber;
                    multiply = false;
                } else if (ratio) {
                    result = firstNumber / secondNumber;
                    ratio = false;
                } else if (percent) {
                    result = 0;
                    percent = false;
                }
                stringResult = String.valueOf(result);
                textView1.setText("");
                textView3.setText("");
                textView2.setText(stringResult);
                break;
            case R.id.button9 :
                textView2.append("9");
                break;
            case R.id.button8 :
                textView2.append("8");
                break;
            case R.id.button7 :
                textView2.append("7");
                break;
            case R.id.button6 :
                textView2.append("6");
                break;
            case R.id.button5 :
                textView2.append("5");
                break;
            case R.id.button4 :
                textView2.append("4");
                break;
            case R.id.button3 :
                textView2.append("3");
                break;
            case R.id.button2 :
                textView2.append("2");
                break;
            case R.id.button1 :
                textView2.append("1");
                break;
            case R.id.button0 :
                textView2.append("0");
                break;

        }
    }

    public String removeChar(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
