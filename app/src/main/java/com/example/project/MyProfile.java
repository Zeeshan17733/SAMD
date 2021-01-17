package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {
    EditText name,age,email,phoneNumber;
    Button save,back,logout;
    FirebaseAuth auth;
    FirebaseFirestore store;
    ProgressBar progress;
    FirebaseUser user;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        name=(EditText)findViewById(R.id.nameTxt);
        age=(EditText)findViewById(R.id.ageTxt);
        email=(EditText)findViewById(R.id.registerEmailTxt);
        phoneNumber=(EditText)findViewById(R.id.registerPhoneTxt);
        progress=(ProgressBar)findViewById(R.id.registerProgressBar);
        save=(Button) findViewById(R.id.saveBtn);
        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();
        back=(Button)findViewById(R.id.backBtn);
        logout=(Button)findViewById(R.id.logout);
        user=auth.getCurrentUser();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()||email.getText().toString().isEmpty()||phoneNumber.getText().toString().isEmpty()||age.getText().toString().isEmpty()){
                    Toast.makeText(MyProfile.this,"One or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String mail=email.getText().toString();
                user.updateEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference doc=store.collection("users").document(user.getUid());
                        Map<String,Object> edited=new HashMap<>();
                        edited.put("Email",mail);
                        edited.put("Full Name",name.getText().toString());
                        edited.put("Phone Number",phoneNumber.getText().toString());
                        edited.put("Age",age.getText().toString());
                        doc.update(edited);
                        Toast.makeText(MyProfile.this,"Email is changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyProfile.this, MainActivity.class));
                finish();
            }
        });
    }
}