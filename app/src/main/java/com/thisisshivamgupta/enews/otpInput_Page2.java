package com.thisisshivamgupta.enews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpInput_Page2 extends AppCompatActivity {

    Button verifyButton;
    EditText inputNumber1,inputNumber2,inputNumber3,inputNumber4,inputNumber5,inputNumber6;
    String getOTPBackEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_input_page2);

        verifyButton=findViewById(R.id.buttonverifyotp);

        inputNumber1=findViewById(R.id.otpinput1);
        inputNumber2=findViewById(R.id.otpinput2);
        inputNumber3=findViewById(R.id.otpinput3);
        inputNumber4=findViewById(R.id.otpinput4);
        inputNumber5=findViewById(R.id.otpinput5);
        inputNumber6=findViewById(R.id.otpinput6);

        TextView showNumberPage2= findViewById(R.id.mobile_number_page2);

        showNumberPage2.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")
        ));

        getOTPBackEnd=getIntent().getStringExtra("backEndOTP");

        final ProgressBar progressBarverifyotp = findViewById(R.id.progressbar_verify_otp);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!inputNumber1.getText().toString().trim().isEmpty()&&
                        !inputNumber2.getText().toString().trim().isEmpty()&&
                        !inputNumber3.getText().toString().trim().isEmpty()&&
                        !inputNumber4.getText().toString().trim().isEmpty()&&
                        !inputNumber5.getText().toString().trim().isEmpty()&&
                        !inputNumber6.getText().toString().trim().isEmpty()) {
                    String enterCodeOTP= inputNumber1.getText().toString().trim()+
                            inputNumber2.getText().toString().trim()+
                            inputNumber3.getText().toString().trim()+
                            inputNumber4.getText().toString().trim()+
                            inputNumber5.getText().toString().trim()+
                            inputNumber6.getText().toString().trim();
                    if(getOTPBackEnd!=null){
                        progressBarverifyotp.setVisibility(View.VISIBLE);
                        verifyButton.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                getOTPBackEnd,enterCodeOTP
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarverifyotp.setVisibility(View.GONE);
                                        verifyButton.setVisibility(View.VISIBLE);

                                        if(task.isSuccessful()){
                                            Intent intent =new Intent(getApplicationContext(),dashboard_page3.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(otpInput_Page2.this, "Invalid OTP!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    }else{
                        Toast.makeText(otpInput_Page2.this, "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(otpInput_Page2.this, "Invalid OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberOtpMove();
        findViewById(R.id.textResendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60, TimeUnit.SECONDS,
                        otpInput_Page2.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(otpInput_Page2.this, e.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newBackendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getOTPBackEnd=newBackendOTP;
                                Toast.makeText(otpInput_Page2.this, "Resent Successfully!", Toast.LENGTH_SHORT).show();


                            }
                        }
                );

            }
        });
    }

    private void numberOtpMove() {
        inputNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputNumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputNumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                {
                    inputNumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty())
                    inputNumber5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputNumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}