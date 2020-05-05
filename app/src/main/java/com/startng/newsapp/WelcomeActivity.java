package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    Timer change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        change = new Timer();
        change.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent next = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(next);
                finish();
            }
        }, 3500);
    }
}
