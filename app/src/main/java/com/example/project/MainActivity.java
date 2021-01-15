package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPass;
    private EditText emailEditText, passwordEditText;
    private Button BtnLogin;
    private ProgressBar progressBar;
    private CheckBox passwordCheck;
    private ProgressBar progress;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register =(TextView) findViewById(R.id.registerTxtView);
        register.setOnClickListener(this);

        forgotPass = (TextView) findViewById(R.id.forgotTxtView);
        forgotPass.setOnClickListener(this);

        progress=(ProgressBar)findViewById(R.id.loginProgress);
        BtnLogin = (Button) findViewById(R.id.loginBtn);
        BtnLogin.setOnClickListener(this);
        emailEditText =(EditText) findViewById(R.id.emailTxt);
        passwordEditText = (EditText) findViewById(R.id.passwordTxt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        passwordCheck =(CheckBox) findViewById(R.id.showPasswordBox);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.registerTxtView:
                startActivity(new Intent(this, Registration.class));
                break;

            case R.id.loginBtn:
                userLogin();
                break;

            case R.id.forgotTxtView:
                //startActivity(new Intent(this, ForgotPassword.class));
                break;
        }

    }

    private void userLogin() {
        String email=emailEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            emailEditText.setError("Email is required");
            return;
        }
        if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Password is required");
            return;
        }
        if(password.length()<6){
            passwordEditText.setError("Password should be more than or equal to 6 characters");
            return;
        }
        progress.setVisibility(View.VISIBLE);
        //authenticatation

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"logged in successfully",Toast.LENGTH_SHORT).show();

                    //Redirected to home page in case of successful login
                   /// startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(MainActivity.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }
        });
    }
}