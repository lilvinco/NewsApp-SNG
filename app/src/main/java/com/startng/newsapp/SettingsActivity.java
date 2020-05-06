package com.startng.newsapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Spinner spinnerFonSize = findViewById(R.id.textViewSizeSpinner);
        ArrayList<String> fontSize = new ArrayList<>();

        for (int i = 12; i < 24; i++){
            fontSize.add(""+ i);
        }
        ArrayAdapter<String> fontSizeSpinnerAdapter = new ArrayAdapter<String >(this,R.layout.activity_setting, fontSize);
        fontSizeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerFonSize.setAdapter(fontSizeSpinnerAdapter);

    }
}
