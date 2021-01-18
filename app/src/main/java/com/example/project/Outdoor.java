package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class Outdoor extends AppCompatActivity {

    ImageButton tableOne, tableTwo, tableThree, tableFour, tableFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);

        tableOne = (ImageButton) findViewById(R.id.tableOneOutdoor);
        tableTwo = (ImageButton) findViewById(R.id.tableTwoOutdoor);
        tableThree = (ImageButton) findViewById(R.id.tableThreeOutdoor);
        tableFour = (ImageButton) findViewById(R.id.tableFourOutdoor);
        tableFive = (ImageButton) findViewById(R.id.tableFiveOutdoor);

        tableOne.setClickable(false);

    }
}