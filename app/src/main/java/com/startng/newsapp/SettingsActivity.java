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


        ArrayList<String> fontSizes = new ArrayList<>();
        fontSizes.add("12");
        fontSizes.add("14");
        fontSizes.add("16");
        fontSizes.add("18");
        fontSizes.add("20");

        ArrayAdapter<String> fontSizeAdapter = new ArrayAdapter<>(this, R.layout.activity_setting, fontSizes);
        fontSizeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerFonSize.setAdapter(fontSizeAdapter);

//        fontSizeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinnerFonSize.setAdapter(fontSizeSpinnerAdapter);
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

    }
}
