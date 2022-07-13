package com.example.android_start.calculator;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.example.android_start.R;

public enum Theme {
    BASE (R.style.StyleBase, R.string.style_base, "base"),
    STYLE1(R.style.Style1, R.string.style1, "style1"),
    STYLE2(R.style.Style2, R.string.style2, "style2");


    Theme(int themeRes, int title, String key) {
        this.themeRes = themeRes;
        this.title = title;
        this.key = key;
    }

    @StyleRes
    private final int themeRes;
    @StringRes
    private final int title;
    private final String key;

    public int getThemeRes() {
        return themeRes;
    }

    public int getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }
}
