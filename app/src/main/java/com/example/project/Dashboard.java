package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    private Button myBookings, addBooking, profile;
    private TextView logoutDash, changePassDash;
    private FirebaseAuth auth;
    private FirebaseFirestore store;
    String userId,name,email,age,phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myBookings = (Button) findViewById(R.id.dashMyBookingsBtn);
        myBookings.setOnClickListener(this);

        addBooking = (Button) findViewById(R.id.dashBookTableBtn);
        addBooking.setOnClickListener(this);

        profile = (Button) findViewById(R.id.dashMyProfileBtn);
        profile.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();
        logoutDash = (TextView) findViewById(R.id.dashLogoutTxtView);
        logoutDash.setOnClickListener(this);

        userId=auth.getCurrentUser().getUid();
        DocumentReference documentReference=store.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name=value.getString("Full Name");
                email=value.getString("Email");
                age=value.getString("Age");
                phoneNumber=value.getString("Phone Number");
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.dashLogoutTxtView:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
                finish();
                break;

            case R.id.dashBookTableBtn:
                // add Booking
                //goto table booking class
                startActivity(new Intent(Dashboard.this, TableBooking.class));
                finish();
                break;

            case R.id.dashMyBookingsBtn:
                //my Bookings
                Intent in=new Intent(Dashboard.this,MyBookings.class);
                in.putExtra("email",email);
                startActivity(in);
                break;

            case R.id.dashMyProfileBtn:
                Intent i=new Intent(Dashboard.this,MyProfile.class);
                i.putExtra("name",name);
                i.putExtra("email",email);
                i.putExtra("phone",phoneNumber);
                i.putExtra("age",age);
                startActivity(i);
                break;

        }
    }
}