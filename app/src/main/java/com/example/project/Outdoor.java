package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class Outdoor extends AppCompatActivity {

    ImageButton tableOne, tableTwo, tableThree, tableFour, tableFive;
    Button continueBtn;
    String date, time;

    FirebaseAuth auth;
    FirebaseFirestore store;
    String userId,name,email,phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);

        tableOne = (ImageButton) findViewById(R.id.tableOneOutdoor);
        tableTwo = (ImageButton) findViewById(R.id.tableTwoOutdoor);
        tableThree = (ImageButton) findViewById(R.id.tableThreeOutdoor);
        tableFour = (ImageButton) findViewById(R.id.tableFourOutdoor);
        tableFive = (ImageButton) findViewById(R.id.tableFiveOutdoor);

        continueBtn = (Button) findViewById(R.id.continueButton);

        //tableOne.setClickable(false);
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");


        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();


        userId=auth.getCurrentUser().getUid();
        DocumentReference documentReference=store.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name=value.getString("Full Name");
                email=value.getString("Email");
                //age=value.getString("Age");
                phoneNumber=value.getString("Phone Number");
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("Name", name);
                map.put("Email", email);
                map.put("Ph # ", phoneNumber);
                map.put("Date", date);
                map.put("Time", time);
                map.put("Table # ", "xyz");

                FirebaseDatabase.getInstance().getReference().child("Reservations").
                        child("Outdoor").updateChildren(map);
            }
        });



    }
}