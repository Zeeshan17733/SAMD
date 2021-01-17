package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    private Button myBookings, addBooking, profile;
    private TextView logoutDash, changePassDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myBookings = (Button) findViewById(R.id.dashMyBookingsBtn);
        myBookings.setOnClickListener(this);

        addBooking = (Button) findViewById(R.id.dashBookTableBtn);
        addBooking.setOnClickListener(this);

        logoutDash = (TextView) findViewById(R.id.dashLogoutTxtView);
        logoutDash.setOnClickListener(this);



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
                break;

            case R.id.dashMyProfileBtn:
                startActivity(new Intent(Dashboard.this,MyProfile.class));
                break;

        }
    }
}