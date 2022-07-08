package com.example.android_start.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android_start.R;
import com.example.android_start.calculator.CalculatorImpl;
import com.example.android_start.calculator.Operation;

import java.io.Serializable;
import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity implements CalculatorDisplay, Serializable {

    private TextView resultTxt;
    private CalculatorPresenter calculatorPresenter;
    private final static String KEY_CALCULATOR = "key1";
    private final static String KEY_DISPLAY = "key2";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CALCULATOR, calculatorPresenter);
        outState.putString(KEY_DISPLAY, (String) resultTxt.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorPresenter = (CalculatorPresenter) savedInstanceState.getSerializable(KEY_CALCULATOR);
        display(savedInstanceState.getString(KEY_DISPLAY));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTxt = findViewById(R.id.display);
        calculatorPresenter = new CalculatorPresenter(new CalculatorImpl(), this);

        HashMap<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.button_1, 1);
        digits.put(R.id.button_2, 2);
        digits.put(R.id.button_3, 3);
        digits.put(R.id.button_4, 4);
        digits.put(R.id.button_5, 5);
        digits.put(R.id.button_6, 6);
        digits.put(R.id.button_7, 7);
        digits.put(R.id.button_8, 8);
        digits.put(R.id.button_9, 9);
        digits.put(R.id.button_0, 0);


        // Создание OnClickListener для каждой кнопки: группового для цифровых кнопок и кнопок операторов
        // и анонимных для других кнопок.

        View.OnClickListener digitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onDigitPressed(digits.get(view.getId()));
            }
        };

        findViewById(R.id.button_1).setOnClickListener(digitClickListener);
        findViewById(R.id.button_2).setOnClickListener(digitClickListener);
        findViewById(R.id.button_3).setOnClickListener(digitClickListener);
        findViewById(R.id.button_4).setOnClickListener(digitClickListener);
        findViewById(R.id.button_5).setOnClickListener(digitClickListener);
        findViewById(R.id.button_6).setOnClickListener(digitClickListener);
        findViewById(R.id.button_7).setOnClickListener(digitClickListener);
        findViewById(R.id.button_8).setOnClickListener(digitClickListener);
        findViewById(R.id.button_9).setOnClickListener(digitClickListener);
        findViewById(R.id.button_0).setOnClickListener(digitClickListener);

        HashMap<Integer, Operation> operators = new HashMap<>();
        operators.put(R.id.button_addition, Operation.ADDITION);
        operators.put(R.id.button_multiplication, Operation.MULTIPLICATION);
        operators.put(R.id.button_divide, Operation.DIVIDE);
        operators.put(R.id.button_subtract, Operation.SUBTRACT);

        View.OnClickListener operatorsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onOperatorsPressed(operators.get(view.getId()));
            }
        };

        findViewById(R.id.button_addition).setOnClickListener(operatorsClickListener);
        findViewById(R.id.button_multiplication).setOnClickListener(operatorsClickListener);
        findViewById(R.id.button_divide).setOnClickListener(operatorsClickListener);
        findViewById(R.id.button_subtract).setOnClickListener(operatorsClickListener);

        findViewById(R.id.button_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onPointPressed();
            }
        });

        findViewById(R.id.button_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onCleanPressed();
            }
        });

        findViewById(R.id.button_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onEqualsPressed();
            }
        });

        findViewById(R.id.button_percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onPercentPressed();
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorPresenter.onDeletePressed();
            }
        });

    }

    /**
     * Метод выводит на дисплей соответствующию строковую информацию.
     *
     * @param result
     */
    @Override
    public void display(String result) {
        resultTxt.setText(result);
    }
}