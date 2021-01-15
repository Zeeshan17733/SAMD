package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText rName,rAge,rEmail,rPassword,rPhoneNumber;
    Button registerBtn;
    FirebaseAuth auth;
    FirebaseFirestore store;
    ProgressBar progress;
    String userId;
    String password,fullName,email,phone,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        rName=(EditText)findViewById(R.id.nameTxt);
        rAge=(EditText)findViewById(R.id.ageTxt);
        rEmail=(EditText)findViewById(R.id.registerEmailTxt);
        rPassword=(EditText)findViewById(R.id.registerPasswordTxt);
        rPhoneNumber=(EditText)findViewById(R.id.registerPhoneTxt);
        progress=(ProgressBar)findViewById(R.id.registerProgressBar);
        registerBtn=(Button) findViewById(R.id.registerBtn);
        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        ////////   in case of successful login user will be redirected to main page
//        if(auth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password=rPassword.getText().toString().trim();
                email=rEmail.getText().toString().trim();
                fullName=rName.getText().toString();
                age=rAge.getText().toString();
                phone=rPhoneNumber.getText().toString();
                if(TextUtils.isEmpty(email)){
                    rEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    rPassword.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    rPhoneNumber.setError("Phone Number is required");
                    return;
                }
                if(password.length()<6){
                    rPassword.setError("Password should be more than or equal to 6 characters");
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                //Creating user in database
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this,"user Created",Toast.LENGTH_SHORT).show();
                            userId=auth.getCurrentUser().getUid();
                            DocumentReference documentReference=store.collection("users").document(userId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("Full Name",fullName);
                            user.put("Email",email);
                            user.put("Age",age);
                            user.put("Phone Number",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                }
                            });


                            // TODO


                           ///// startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Registration.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}