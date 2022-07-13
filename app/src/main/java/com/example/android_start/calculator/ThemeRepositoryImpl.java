package com.example.android_start.calculator;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

public class ThemeRepositoryImpl implements ThemeRepository{

    private static ThemeRepository INSTANCE;
    private static final String KEY_THEME = "keyTheme";
    private SharedPreferences preferences;

    private ThemeRepositoryImpl(Context context) {
       preferences = context.getSharedPreferences("themes.xml", Context.MODE_PRIVATE);
    }

    public static ThemeRepository getInstance(Context context) {
        if (INSTANCE==null)
            INSTANCE = new ThemeRepositoryImpl(context);
        return INSTANCE;
    }

    @Override
    public Theme getSaveTheme() {
        String saveThemeKey = preferences.getString(KEY_THEME, Theme.BASE.getKey());
         for (Theme them: Theme.values())
             if (them.getKey().equals(saveThemeKey))
                 return them;
         return Theme.BASE;
    }

    @Override
    public void saveTheme(Theme theme) {
        preferences.edit()
                .putString(KEY_THEME,theme.getKey())
                .apply();
    }

    @Override
    public List<Theme> getAllThemes() {
        return Arrays.asList(Theme.values());
    }
}
