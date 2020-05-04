package com.startng.newsapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dtechatoms.notetaker.Helper.ToastAndSnacksbar;
import com.dtechatoms.notetaker.MainActivity;
import com.dtechatoms.notetaker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private Button mLogin;
    private EditText phoneNumber, mPassWord, mOTP;
    private CountryCodePicker mCountryCode;

    private String verivicatioID;
    private PhoneAuthProvider.ForceResendingToken token;

    private Boolean verificationInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = findViewById(R.id.sigin_button);
        phoneNumber = findViewById(R.id.phone_edit_text);
        mPassWord = findViewById(R.id.password_edit_text);
        mOTP = findViewById(R.id.OTP_edit_text);
        mCountryCode = findViewById(R.id.country_code_picker);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!verificationInProgress){ // Trigger the condition to be True

                    String passWord = mPassWord.getText().toString().trim();

                    String phoneNum = (phoneNumber.getText().toString());
                    String countryCode = mCountryCode.getSelectedCountryCodeWithPlus();
                    String completeNumber = countryCode + phoneNum;


                    if (TextUtils.isEmpty(phoneNum)) {
                        ToastAndSnacksbar.ToastMessage(getApplication(), v, "Fields must not be empty");
                        return;
                    } else if (completeNumber.length() < 11) {
                        phoneNumber.setError("Value is incomplete");
                        return;
                    } else {

                        requestOTP(completeNumber);
                    }
                } else {

                    String OTP = mOTP.getText().toString();
                    if (TextUtils.isEmpty(OTP)){
                        mOTP.setError("Value cannot be empty");
                        return;
                    } else if (OTP.length() < 6){
                        mOTP.setError("OTP is incomplete");
                    } else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verivicatioID, OTP);
                        verifyAuth(credential);

                    }

                }


            }
        });
    }

    private void verifyAuth(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    ToastAndSnacksbar.ToastMessage(getApplicationContext(), mLogin, "Success");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                } else {
                    ToastAndSnacksbar.ToastMessage(getApplicationContext(), mLogin, "Unsuccessful");
                }
            }
        });
    }

    private void requestOTP(String completeNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(completeNumber, 60L, TimeUnit.SECONDS,
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        ToastAndSnacksbar.ToastMessage(getApplicationContext(), mLogin, e.getMessage());

                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verivicatioID = s;
                        token = forceResendingToken;

                        mOTP.setVisibility(View.VISIBLE);
                        phoneNumber.setVisibility(View.GONE);
                        mLogin.setText("Verify");
                        verificationInProgress = true;
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                    }
                });

    }


}
