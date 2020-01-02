package com.gmail.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    enum State { PLUS, MINUS, MULTIPLY, RATIO, PERCENT, OFF }

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    private State state;

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
        findViewById(R.id.buttonC).setOnClickListener(this);
        findViewById(R.id.buttonRatio).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonBackspace).setOnClickListener(this);
        findViewById(R.id.buttonMinus).setOnClickListener(this);
        findViewById(R.id.buttonPlus).setOnClickListener(this);
        findViewById(R.id.buttonPercent).setOnClickListener(this);
        findViewById(R.id.buttonPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button0).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonC :
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");
                state = State.OFF;
                break;
            case R.id.buttonRatio :
                textView1.setText("/");
                state = State.RATIO;
                changeText();
                break;
            case R.id.buttonMultiply :
                textView1.setText("*");
                state = State.MULTIPLY;
                changeText();
                break;
            case R.id.buttonBackspace :
                String str = removeChar(textView2.getText().toString());
                textView2.setText(str);
                break;
            case R.id.buttonMinus :
                textView1.setText("-");
                state = State.MINUS;
                changeText();
                break;
            case R.id.buttonPlus :
                textView1.setText("+");
                state = State.PLUS;
                changeText();
                break;
            case R.id.buttonPercent :
                secondNumber = Double.valueOf(textView2.getText().toString());
                if (state == State.PLUS) {
                    stringResult = String.valueOf(firstNumber + (firstNumber * secondNumber / 100));
                    state = State.OFF;
                } else if (state == State.MINUS) {
                    stringResult = String.valueOf(firstNumber - (firstNumber * secondNumber / 100));
                    state = State.OFF;
                } else if (state == State.MULTIPLY) {
                    stringResult = String.valueOf(firstNumber * secondNumber / 100);
                    state = State.OFF;
                } else if (state == State.RATIO) {
                    stringResult = String.valueOf(firstNumber / secondNumber * 100);
                    state = State.OFF;
                }
                state = State.PERCENT;
                textView1.setText("");
                textView3.setText("");
                textView2.setText(stringResult);
                break;
            case R.id.buttonPoint :
                textView2.append(".");
                break;
            case R.id.buttonEquals :
                secondNumber = Double.valueOf(textView2.getText().toString());
                if (state == State.PLUS) {
                    result = firstNumber + secondNumber;
                } else if (state == State.MINUS) {
                    result = firstNumber - secondNumber;
                } else if (state == State.MULTIPLY) {
                    result = firstNumber * secondNumber;
                } else if (state == State.RATIO) {
                    result = firstNumber / secondNumber;
                } else if (state == State.PERCENT) {
                    result = 0;
                }
                state = State.OFF;
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

    public void changeText() {
        firstNumber = Double.valueOf(textView2.getText().toString());
        textView3.setText(textView2.getText().toString());
        textView2.setText("");
    }
}
