package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

public class Indoor extends AppCompatActivity {

    ImageButton tableOne, tableTwo, tableThree, tableFour, tableFive, tableSix;
    Button continueBtn;
    String date, time;

    FirebaseAuth auth;
    FirebaseFirestore store;

    String userId,name,email,phoneNumber;
    String tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        tableOne = (ImageButton) findViewById(R.id.tableOneIndoor);
        tableTwo = (ImageButton) findViewById(R.id.tableTwoIndoor);
        tableThree = (ImageButton) findViewById(R.id.tableThreeIndoor);
        tableFour = (ImageButton) findViewById(R.id.tableFourIndoor);
        tableFive = (ImageButton) findViewById(R.id.tableFiveIndoor);
        tableSix = (ImageButton) findViewById(R.id.tableSixIndoor);

        continueBtn = (Button) findViewById(R.id.continueIndoorButton);

        tableOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.GREEN);
                tableTwo.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.WHITE);
                tableSix.setBackgroundColor(Color.WHITE);
                tableNumber = "1";
            }
        });

        tableTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.WHITE);
                tableTwo.setBackgroundColor(Color.GREEN);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.WHITE);
                tableSix.setBackgroundColor(Color.WHITE);
                tableNumber = "2";
            }
        });

        tableThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.WHITE);
                tableTwo.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.GREEN);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.WHITE);
                tableSix.setBackgroundColor(Color.WHITE);
                tableNumber = "3";
            }
        });

        tableFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.WHITE);
                tableTwo.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.GREEN);
                tableFive.setBackgroundColor(Color.WHITE);
                tableSix.setBackgroundColor(Color.WHITE);
                tableNumber = "4";
            }
        });

        tableFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.WHITE);
                tableTwo.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.GREEN);
                tableSix.setBackgroundColor(Color.WHITE);
                tableNumber = "5";
            }
        });

        tableSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.WHITE);
                tableTwo.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.WHITE);
                tableSix.setBackgroundColor(Color.GREEN);
                tableNumber = "6";
            }
        });

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
                map.put("Table # ", tableNumber);

                FirebaseDatabase.getInstance().getReference().child("Reservations").
                        child("Indoor").updateChildren(map);
            }
        });

    }
}