package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.familyapp.objects.Event;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.time.LocalDateTime;

public class NewEvent extends AppCompatActivity {

    int year_;
    int month_;
    int day_;
    int hour_;
    int minutes_;
    Boolean boolDate = false;
    Boolean boolTime = false;
    TextView timeTV, dateTV;
    User user;
    long userId;
    OpenHelper db;
    EditText nameOfEvent;
    Boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);


        nameOfEvent = findViewById(R.id.nameOfEvent);
        timeTV = findViewById(R.id.timeTV);
        dateTV = findViewById(R.id.dateTV);
        db = new OpenHelper(this);

        Intent intent = getIntent();
        userId = intent.getExtras().getLong("userId");

        db.open();
        user = db.findUser(userId);
        db.close();

        isNew = intent.getExtras().getBoolean("isNew");
        if (!isNew){
            nameOfEvent.setText(intent.getExtras().getString("eventName"));
        }



    }

    public void date(View view) {
        launchDateDialog();


    }

    public void save(View view) {

        if (nameOfEvent.length() == 0 || boolDate == false || boolTime == false){
            return;
        }


        User owner = db.findUser(userId);
        Event e = new Event(owner.getFamilyId(), userId, 0, nameOfEvent.getText().toString(), year_, month_, day_, hour_, minutes_);



        if (isNew) {
            db.open();
            e = db.saveEvent(e);
            db.close();
        }
        else{
            long id = getIntent().getExtras().getLong("eventId");
            e.setId(id);
            db.open();
            db.updateByRow(e);
            System.out.println("fnriegh");
            db.close();
        }


        Intent intent = new Intent();

        setResult(RESULT_OK, intent);
        finish();

    }

    public void cancel(View view) {
        Intent intent = new Intent();

        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void time(View view) {
        launchTimeDialog();

    }


    public void launchDateDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setTitle("TITLE");
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_ = year;
                month_ = month + 1;
                day_ = dayOfMonth;

                dateTV.setText(day_ +"/" + month_+ "/" + year_ );
                dateTV.setVisibility(View.VISIBLE);

                boolDate = true;
            }
        });
        dialog.show();
    }

    public void launchTimeDialog(){
        LocalDateTime time = LocalDateTime.now();
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hour_ = hourOfDay;
                minutes_ = minute;

                String sHour = ""+hour_;
                String sMinute = ""+minutes_;

                if (hour_ < 10){
                    sHour = "0"+hour_;
                }

                if (minutes_ < 10){
                    sMinute = "0"+minutes_;
                }

                timeTV.setText(sHour +":" + sMinute );
                timeTV.setVisibility(View.VISIBLE);

                boolTime = true;
            }
        }, time.getHour(), time.getMinute(), true);

        dialog.setTitle("TITLE");
        dialog.show();


    }
}