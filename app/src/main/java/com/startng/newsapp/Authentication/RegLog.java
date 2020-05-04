package com.startng.newsapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dtechatoms.notetaker.Helper.ToastAndSnacksbar;
import com.dtechatoms.notetaker.MainActivity;
import com.dtechatoms.notetaker.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegLog extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);


    }

    public void anonymousLogin(View view) {
        firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ToastAndSnacksbar.onlyToast(getApplicationContext(), "Some error occured");
            }
        });
    }

    public void registerBtn(View view) {

        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

    }

    public void loginBtn(View view) {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    protected void onStart() {
        if(firebaseAuth.getCurrentUser() != null){  // If user is logged in
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        super.onStart();
    }
}
