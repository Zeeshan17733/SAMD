package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class Indoor extends AppCompatActivity {

    ImageButton tableOne, tableTwo, tableThree, tableFour, tableFive, tableSix;
    Button continueBtn;
    String date, time;

    FirebaseAuth auth;
    FirebaseFirestore store;
    FirebaseDatabase database;
    DatabaseReference reference;
    String userId,name,email,phoneNumber;
    String tableNumber;
    String table1,date1,time1;

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


                final DatabaseReference mDatabaseRef = database.getReference().child("Reservation").child("Indoor");
//                mDatabaseRef.orderByChild("date_time").equalTo(date,time).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot d: data.getChildren()){
//                            yourClass work = d.getValue(yourClass.class);
//                            if(work.getGender().equals("Male"){
//                                double salary = work.getSalary();
//                                if(salary  > 5000 && salary < 15000){
//                                    //save the worker into a list.
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//            });
                mDatabaseRef.orderByChild("date_time").equalTo(date,time).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            table1 = userSnapshot.child("tableNumber").getValue(String.class);
                            date1 = dataSnapshot.child("date").getValue(String.class);
                            time1=dataSnapshot.child("time").getValue(String.class);
                            if(table1.equals(tableNumber)&&date1.equals(date)&& time1.equals(time) ){
                                Toast.makeText(Indoor.this,"Table already booked",Toast.LENGTH_SHORT).show();
                            }
                        }

                        Toast.makeText(Indoor.this,table1,Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                SaveData saveData=new SaveData(name,email,phoneNumber,date,time,tableNumber);

                DatabaseReference databaseReference = database.getReference().child("Reservations").child("Indoor").child(userId);
                databaseReference.setValue(saveData);

                startActivity(new Intent(getApplicationContext(), Dashboard.class));


            }


                });

    }
}