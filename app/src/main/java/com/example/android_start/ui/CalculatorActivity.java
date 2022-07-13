package com.example.android_start.ui;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android_start.calculator.Theme;
import com.example.android_start.calculator.ThemeRepository;
import com.example.android_start.calculator.ThemeRepositoryImpl;
import com.example.android_start.R;
import com.example.android_start.calculator.CalculatorImpl;
import com.example.android_start.calculator.Operation;

import java.util.HashMap;


public class CalculatorActivity extends AppCompatActivity implements CalculatorDisplay {

    private TextView resultTxt;
    private CalculatorPresenter calculatorPresenter;
    private ThemeRepository themeRepository;
    private final static String KEY_DISPLAY = "key1";
    private final static String KEY_CALCULATOR = "key2";


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CALCULATOR, calculatorPresenter); // сохраняем текущие значения полей calculatorPresenter
        outState.putString(KEY_DISPLAY, (String) resultTxt.getText()); // сохраняем текущее значение с дисплея
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorPresenter = savedInstanceState.getParcelable(KEY_CALCULATOR); // восстанавливаем сохраненные значения полей calculatorPresenter
        display(savedInstanceState.getString(KEY_DISPLAY));// восстанавливаем сохраненное значение дисплея и выводим его на дисплей
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeRepository = ThemeRepositoryImpl.getInstance(this);
        setTheme(themeRepository.getSaveTheme().getThemeRes());// выставляем тему приложения
        setContentView(R.layout.activity_main);

        resultTxt = findViewById(R.id.display);
        calculatorPresenter = new CalculatorPresenter(new CalculatorImpl(), this);

        // Создание OnClickListener для каждой кнопки: группового для цифровых кнопок и кнопок операторов
        // и анонимных для других кнопок.

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


        // Создаем Launcher для запуска CalculatorMenuActivity
        ActivityResultLauncher<Intent> themeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                assert intent != null;
                Theme selectedTheme = (Theme) intent.getSerializableExtra(CalculatorMenuActivity.EXTRA_THEME);
                themeRepository.saveTheme(selectedTheme);
                recreate();
            }
        });

        // Запуск активи CalculatorMenuActivity c получением результата в виде выбранной темы
        findViewById(R.id.button_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculatorActivity.this, CalculatorMenuActivity.class);
                intent.putExtra(CalculatorMenuActivity.EXTRA_THEME, themeRepository.getSaveTheme());
                themeLauncher.launch(intent);
            }
        });

        // Переход на сайт https://gb.ru
        findViewById(R.id.button_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gb.ru"));
                startActivity(Intent.createChooser(browserIntent, null));
            }
        });

    }

    @Override
    public void display(String result) {
        resultTxt.setText(result);
    }


}