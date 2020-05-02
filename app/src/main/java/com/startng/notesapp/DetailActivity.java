package com.startng.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Find textviews, get the extras and assign both to each other
        TextView textView = findViewById(R.id.textView3);
        String headline = getIntent().getStringExtra("headline");
        textView.setText(headline);
    }
}
