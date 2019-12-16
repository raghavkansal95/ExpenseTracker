package com.example.expensetracker.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.Database.SharedPref;
import com.example.expensetracker.Model.User;
import com.example.expensetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.rilixtech.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class EnterPhoneNumber extends AppCompatActivity {
    EditText inputPhoneNumber;
    Button submitPhoneNumber;
    CountryCodePicker ccp;
    ImageView ImageEnterNumber;
    TextView textViewVerifyNumber;
    TextView textViewToReceiveOtp;
    LinearLayout linearLayoutEnterNumber;

    OtpView otpView;
    Button resendOtp;

    String phoneNumber;
    String completePhoneNumber;
    String verificationCode;

    SharedPref sharedPref;
    RealmDatabase realmDatabase;
    FirebaseAuth auth;
    PhoneAuthProvider.ForceResendingToken mtoken;


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone);

        ImageEnterNumber=findViewById(R.id.ImageEnterNumber);
        textViewVerifyNumber=findViewById(R.id.textViewVerifyNumber);
        textViewToReceiveOtp=findViewById(R.id.textViewToReceiveOtp);
        linearLayoutEnterNumber=findViewById(R.id.linearLayoutEnterNumber);
        inputPhoneNumber=findViewById(R.id.inputPhoneNumber);
        submitPhoneNumber=findViewById(R.id.submitPhoneNumber);
        ccp=findViewById(R.id.countryCodePicker);
        otpView=findViewById(R.id.otp_view);
        resendOtp=findViewById(R.id.resendOtp);

        submitPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code= ccp.getSelectedCountryCodeWithPlus();
                phoneNumber=inputPhoneNumber.getText().toString().trim();
                completePhoneNumber=code.concat(phoneNumber);

                if (phoneNumber.length()!=10){
                    inputPhoneNumber.setError("Please Enter the valid Number");
                }

                else{
                    ImageEnterNumber.setImageResource(R.drawable.messagexxl);
                    textViewVerifyNumber.setText("Verification Code");
                    textViewToReceiveOtp.setText("Enter the OTP sent to phone number"+completePhoneNumber);

                    linearLayoutEnterNumber.setVisibility(View.INVISIBLE);
                    otpView.setVisibility(View.VISIBLE);
                    submitPhoneNumber.setVisibility(View.INVISIBLE);
                    resendOtp.setVisibility(View.VISIBLE);

                    StartFirebaseLogin();

                    sharedPref = new SharedPref(getApplicationContext());
                    realmDatabase=new RealmDatabase(getApplicationContext());

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            completePhoneNumber,
                            60,
                            TimeUnit.SECONDS,
                            EnterPhoneNumber.this,
                            mCallback);

                    otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
                        @Override
                        public void onOtpCompleted(String otp) {
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                            SigninWithPhone(credential);
                            Log.d("onOtpCompleted=>", otp);
                        }
                    });
                }
            }
        });

        resendOtp=findViewById(R.id.resendOtp);
        resendOtp.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resendOtp.setEnabled(true);
            }
        },30000);

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resendOtp.setEnabled(true);
                    }
                },30000);
                resendVerificationCode(completePhoneNumber,mtoken);
            }
        });
    }

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(EnterPhoneNumber.this, "verification completed", Toast.LENGTH_SHORT).show();
                otpView.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(EnterPhoneNumber.this, "verification failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                mtoken=forceResendingToken;
                Toast.makeText(EnterPhoneNumber.this, "Code sent", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void SigninWithPhone(final PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User user=new User();
                            user.setPhone(completePhoneNumber);

                            realmDatabase.insertUser(user);
                            sharedPref.createLoginSession();

                            Intent intent=new Intent(EnterPhoneNumber.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EnterPhoneNumber.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                EnterPhoneNumber.this,
                mCallback,
                token);
    }


}
