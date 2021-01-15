package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPass;
    private EditText emailEditText, passwordEditText;
    private Button BtnLogin;
    private ProgressBar progressBar;
    private CheckBox passwordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register =(TextView) findViewById(R.id.registerTxtView);
        register.setOnClickListener(this);

        forgotPass = (TextView) findViewById(R.id.forgotTxtView);
        forgotPass.setOnClickListener(this);

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
    }
}