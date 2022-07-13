package com.example.android_start.calculator;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public interface ThemeRepository {

    Theme getSaveTheme ();
    void saveTheme (Theme theme);
    List<Theme> getAllThemes ();
}
