package com.example.android_start;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Обработка нажатия кнопки - переход на второй макет
    public void next (View view){
        setContentView(R.layout.activity_main2);
    }

    // Обработка нажатия кнопки
    public void sendMessage(View view) {
        TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.name);
        textView.setText(editText.getText() + ", данные сохранены.");
    }
}