package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find textviews, get the extras and assign both to each other
        TextView textView = findViewById(R.id.textView3);
        String headline = getIntent().getStringExtra("headline");
        textView.setText(headline);
    }

    public boolean onCreateOptionMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav, menu);
        return true;
    }
}
