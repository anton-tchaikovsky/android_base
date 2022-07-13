package com.example.android_start.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.android_start.R;


public class BaseActivity extends AppCompatActivity{

    private static final String NameSharedPreference = "LOGIN";

    private static final String AppTheme = "APP_THEME";
    protected static final int CodeStyleBase = 0;
    protected static final int CodeStyle1 = 1;
    protected static final int CodeStyle2 = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Устанавливать тему надо только до установки макета активити
        setTheme(getAppTheme(R.style.StyleBase));
    }
    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }
    // Чтение настроек, параметр «тема»
    protected int getCodeStyle(int codeStyle){
// Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
//Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(AppTheme, codeStyle);
    }
    // Сохранение настроек
    protected void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
// Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }
    private int codeStyleToStyleId(int codeStyle){
        switch(codeStyle){
            case CodeStyle1:
                return R.style.Style1;
            case CodeStyle2:
                return R.style.Style2;

            default:
                return R.style.StyleBase;
        }
    }

}
