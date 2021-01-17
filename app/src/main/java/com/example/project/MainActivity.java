package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPass;
    private EditText emailEditText, passwordEditText;
    private Button BtnLogin;
    private ProgressBar progressBar;
    private CheckBox passwordCheck;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register =(TextView) findViewById(R.id.registerTxtView);


        forgotPass = (TextView) findViewById(R.id.forgotTxtView);
        forgotPass.setOnClickListener(this);

        BtnLogin = (Button) findViewById(R.id.loginBtn);
        BtnLogin.setOnClickListener(this);
        emailEditText =(EditText) findViewById(R.id.emailTxt);
        passwordEditText = (EditText) findViewById(R.id.passwordTxt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        passwordCheck =(CheckBox) findViewById(R.id.showPasswordBox);
        auth=FirebaseAuth.getInstance();


        passwordCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }
                if(password.length()<6){
                    passwordEditText.setError("Password should be more than or equal to 6 characters");
                    passwordEditText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //authenticatation


                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified())
                            {
                                startActivity(new Intent(MainActivity.this, Dashboard.class));
                                progressBar.setVisibility(View.GONE);

                            }
                            else
                            {
                                user.sendEmailVerification();
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Check your email to verify your account",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Failed to Login! Please check your credidentials",
                                    Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.registerTxtView:
                startActivity(new Intent(this, Registration.class));
                break;

            case R.id.forgotTxtView:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId())
//        {
//            case R.id.registerTxtView:
//                startActivity(new Intent(this, Registration.class));
//                break;
//
//            case R.id.loginBtn:
//                userLogin();
//                break;
//
//            case R.id.forgotTxtView:
//                //startActivity(new Intent(this, ForgotPassword.class));
//                break;
//        }




}



//    private void userLogin() {
//
//    }
