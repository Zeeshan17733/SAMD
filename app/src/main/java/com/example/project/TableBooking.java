package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class TableBooking extends AppCompatActivity {

    EditText selectedDate;
    DatePickerDialog date;
    Spinner spinner;
    RadioButton indoorSelection, outdoorSelection;
    RadioGroup radioGroup;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_booking);

        indoorSelection = (RadioButton) findViewById(R.id.indoorRadio);
        outdoorSelection = (RadioButton) findViewById(R.id.outdoorRadio);

        radioGroup = (RadioGroup) findViewById(R.id.selectionGrp);
        submit = (Button) findViewById(R.id.checkBtn);

        selectedDate = (EditText) findViewById(R.id.bookingDate);
        //selectedTime=(EditText)findViewById(R.id.bookingTime);
        //selectedTime.setInputType(InputType.TYPE_NULL);
        spinner=(Spinner) findViewById(R.id.spinner);



        selectedDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Calendar cld=Calendar.getInstance();
                int day=cld.get(Calendar.DAY_OF_MONTH);
                int month=cld.get(Calendar.MONTH);
                int year=cld.get(Calendar.YEAR);
                date=new DatePickerDialog(TableBooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectedDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                    },year,month,day);
                long now = System.currentTimeMillis() - 1000;
                date.getDatePicker().setMinDate(cld.getTimeInMillis());
                date.getDatePicker().setMaxDate(now+(1000*60*60*24*7));
                
                date.show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time=spinner.getSelectedItem().toString().trim();
                String bookingDate = selectedDate.getText().toString().trim();
                if(TextUtils.isEmpty(bookingDate))
                {
                    selectedDate.setError("select a date");
                    return;
                }

                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    indoorSelection.setError("Check any radio button");
                    return;
                }
                else if (indoorSelection.isChecked())
                {
                    Intent intent = new Intent(TableBooking.this, Indoor.class);
                    intent.putExtra("date", bookingDate);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(TableBooking.this, Outdoor.class);
                    intent.putExtra("date", bookingDate);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}