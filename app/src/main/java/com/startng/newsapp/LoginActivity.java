package com.startng.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        Button ok_button = findViewById(R.id.button_login);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome();
            }
        });
    }
    private void welcome(){
        Intent intent = new Intent(LoginActivity.this,HeadlinesActivity.class);
        startActivity(intent);
    }

    private void SignUp(){
        Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signup);
    }

}