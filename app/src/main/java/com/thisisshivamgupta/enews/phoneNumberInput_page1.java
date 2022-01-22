package com.thisisshivamgupta.enews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneNumberInput_page1 extends AppCompatActivity {


    EditText enteredMobileNumber;
    Button getOtpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumberinputpage1);

        enteredMobileNumber = findViewById(R.id.input_mobile_number_field);
        getOtpButton = findViewById(R.id.buttongetotp);
        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enteredMobileNumber.getText().toString().trim().isEmpty()) {
                    if (enteredMobileNumber.getText().toString().trim().length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        getOtpButton.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enteredMobileNumber.getText().toString(),
                                60, TimeUnit.SECONDS,
                                phoneNumberInput_page1.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.INVISIBLE);
                                        Toast.makeText(phoneNumberInput_page1.this, e.getMessage()
                                                , Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backEndOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.INVISIBLE);

                                        Intent intent = new Intent(getApplicationContext(), otpInput_Page2.class);
                                        intent.putExtra("mobile", enteredMobileNumber.getText().toString());
                                        intent.putExtra("backEndOTP",backEndOTP);
                                        startActivity(intent);

                                    }
                                }
                        );

                    } else {
                        Toast.makeText(phoneNumberInput_page1.this, "Please enter correct phone number!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(phoneNumberInput_page1.this, "Enter phone number!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}