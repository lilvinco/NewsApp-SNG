package com.startng.newsapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.startng.newsapp.Helper.ToastAndSnacksbar;
import com.startng.newsapp.MainActivity;
import com.startng.newsapp.R;

public class RegLog extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        progressBar = findViewById(R.id.reglog_progress_bar);


    }

    public void registerBtn(final View view) {
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(RegLog.this, MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ToastAndSnacksbar.onlyToast(getApplicationContext(), "Some error occured");
                view.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void loginBtn(View view) {
        view.setEnabled(false);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        view.setEnabled(true);
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
