package user_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.startng.newsapp.MainActivity;
import com.startng.newsapp.R;

public class RegisterActivity extends AppCompatActivity {
    private Button regButton;
    private TextInputLayout input_password, input_email, input_name;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_email = (TextInputLayout)findViewById(R.id.input_email);
        input_name = (TextInputLayout) findViewById(R.id.input_name);
        input_password = (TextInputLayout) findViewById(R.id.input_password);

        regButton = (Button) findViewById(R.id.btn_reg);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = input_name.getEditText().getText().toString().trim();
                String uemail = input_email.getEditText().getText().toString().trim();
                String upassword = input_password.getEditText().getText().toString().trim();

                registerUser(uname, uemail, upassword);

            }
        });
    }

    private void registerUser(final String name, String email, String password){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing Request, Please wait...");

        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("basic").child("name").setValue(name)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                        Toast.makeText(RegisterActivity.this, "User Data Created", Toast.LENGTH_LONG).show();

                                    } else{
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                } else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

