package com.startng.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import user_register.LoginActivity;
import user_register.RegisterActivity;

public class Start_Activity extends AppCompatActivity {
    private Button button_in, button_up;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);

        button_in = findViewById(R.id.button_signIn);
        button_up = findViewById(R.id.button_signUp);

        firebaseAuth = FirebaseAuth.getInstance();
        updateUI();

        button_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });

    }
    private void register(){
        Intent regIntent = new Intent(Start_Activity.this, RegisterActivity.class);
        startActivity(regIntent);

    }

    private void login(){
        Intent logIntent = new Intent(Start_Activity.this,  LoginActivity.class);
        startActivity(logIntent);

    }

    private void updateUI(){

        if (firebaseAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "firebaseAuth != null");
//            Intent intent = new Intent(Start_Activity.this, MainActivity.class);
//            startActivity(intent);
//            finish();




        }
        else {

            Log.i("MainActivity", "firebaseAuth == null");




        }


   }

}
