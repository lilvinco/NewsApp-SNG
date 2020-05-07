package user_register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.startng.newsapp.MainActivity;
import com.startng.newsapp.R;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout login_email, login_password;
    private Button login_btn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = (TextInputLayout)findViewById(R.id.login_email);
        login_password = (TextInputLayout)findViewById(R.id.login_password);
        login_btn = (Button)findViewById(R.id.login_button);

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getEditText().getText().toString().trim();
                String password = login_password.getEditText().getText().toString().trim();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    logIn(email,password);
                }

            }
        });

    }

    private void logIn(String email, String password){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In, Please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                    Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_LONG).show();
 

                } else {
                    Toast.makeText(LoginActivity.this, "Error: "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
