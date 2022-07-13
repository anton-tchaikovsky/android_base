package com.example.android_start.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android_start.R;
import com.example.android_start.calculator.Theme;
import com.example.android_start.calculator.ThemeRepository;
import com.example.android_start.calculator.ThemeRepositoryImpl;

public class CalculatorMenuActivity extends AppCompatActivity {

    public static final String EXTRA_THEME = "EXTRA_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_menu);

        ThemeRepository themeRepository = ThemeRepositoryImpl.getInstance(this);
        Theme selectedTheme = (Theme)getIntent().getSerializableExtra(EXTRA_THEME);
        themeRepository.saveTheme(selectedTheme);

       RadioButton base = (RadioButton)(findViewById(R.id.radioButtonBase));
        RadioButton style1 = (RadioButton)(findViewById(R.id.radioButtonStyle1));
        RadioButton style2 = (RadioButton)(findViewById(R.id.radioButtonStyle2));

        // Отметка выбранной темы в RadioGroup (при открытии CalculatorMenuActivity)
        if (selectedTheme.equals(Theme.BASE))
            base.setChecked(true);
        if (selectedTheme.equals(Theme.STYLE1))
            style1.setChecked(true);
        if (selectedTheme.equals(Theme.STYLE2))
            style2.setChecked(true);


        // Обработка нажатия кнопок RadioGroup (сохранение выбранной темы)
        RadioGroup radioGroupTheme = (RadioGroup)(findViewById(R.id.radioButtons));
        radioGroupTheme.setOnCheckedChangeListener((radioGroup, id) -> {
            switch(id) {
                case R.id.radioButtonBase:
                   themeRepository.saveTheme(Theme.BASE);

                    break;
                case R.id.radioButtonStyle1:
                   themeRepository.saveTheme(Theme.STYLE1);
                    break;
                case R.id.radioButtonStyle2:
                    themeRepository.saveTheme(Theme.STYLE2);
                    break;
                default:
                    break;
            }
        });

        // Возвращение в активити CalculatorActivity с передачей выбранной темы
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(EXTRA_THEME, themeRepository.getSaveTheme());
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

    }
}