package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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


    String table1,date1,time1;

    FirebaseAuth auth;
    FirebaseFirestore store;
    FirebaseDatabase database;
    String userId,name,email,phoneNumber;
    DatabaseReference reference;

    String tableNumber;
//    ImageButton tableOne,

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

        tableOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOne.setBackgroundColor(Color.GREEN);
                tableTwo.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.WHITE);
                tableNumber = "1";
            }
        });

        tableTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableTwo.setBackgroundColor(Color.GREEN);
                tableOne.setBackgroundColor(Color.WHITE);
                tableThree.setBackgroundColor(Color.WHITE);
                tableFour.setBackgroundColor(Color.WHITE);
                tableFive.setBackgroundColor(Color.WHITE);
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
                tableNumber = "5";
            }
        });

        //tableOne.setClickable(false);
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");


        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

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
                final DatabaseReference mDatabaseRef = database.getReference().child("Reservations").child("Indoor");
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            table1 = userSnapshot.child("tableNumber").getValue(String.class);
                            date1 = userSnapshot.child("date").getValue().toString();
                            time1=userSnapshot.child("time").getValue(String.class);
                            if(table1.equals(tableNumber)&&date1.equals(date)&& time1.equals(time) ){
                                       Toast.makeText(Outdoor.this,"Table not available",Toast.LENGTH_SHORT).show();
                                       return;

                            }
                            else
                            {
                                SaveData saveData=new SaveData(name,email,phoneNumber,date,time,tableNumber, "Outdoor");
                                DatabaseReference databaseReference = database.getReference().child("Reservations").child("Outdoor");
                                databaseReference.push().setValue(saveData);
                                Toast.makeText(Outdoor.this,"Table has been booked",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                break;
                            }

                        }
                    }

                    // Toast.makeText(Indoor.this,date1,Toast.LENGTH_SHORT).show();




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ///////////////////////

            }
        });



    }
}