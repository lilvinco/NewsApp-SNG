package com.startng.newsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView bodyTextView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        titleTextView = findViewById(R.id.textViewTitle);
//        bodyTextView = findViewById(R.id.textViewBody);

        Spinner spinner = findViewById(R.id.textViewSizeSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                float size = Float.parseFloat(parent.getItemAtPosition(position).toString());
                //Todo: implement font size change
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }
}
