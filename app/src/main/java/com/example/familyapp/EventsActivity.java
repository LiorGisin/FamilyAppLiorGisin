package com.example.familyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.familyapp.objects.Event;
import com.example.familyapp.objects.Item;
import com.example.familyapp.objects.Task;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {


    ListView lv;
    ArrayList<Event> eventList;
    EventsAdapter adapter;
    User user;
    long userId;
    Event lastSelected;
    OpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent1 = getIntent();
        userId = intent1.getExtras().getLong("userId");



        db = new OpenHelper(this);

        db.open();
        user = db.findUser(userId);
        eventList = db.getAllEvents(user.getFamilyId());
        db.close();

        lv = findViewById(R.id.lv3);
        adapter = new EventsAdapter(this, 0, 0, eventList);
        lv.setAdapter(adapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                lastSelected = adapter.getItem(i);

                        Intent intent = new Intent(getApplicationContext(), NewEvent.class);
                        intent.putExtra("userId", user.getUserId());
                        intent.putExtra("eventName", lastSelected.getDescription());
                        intent.putExtra("year", lastSelected.getYear());
                        intent.putExtra("month", lastSelected.getMonth());
                        intent.putExtra("day", lastSelected.getDay());
                        intent.putExtra("hour", lastSelected.getHour());
                        intent.putExtra("minute", lastSelected.getMinutes());
                        intent.putExtra("eventId", lastSelected.getId());
                        intent.putExtra("isNew", false);


                        startActivityForResult(intent, 0);


            }

        });




    }



    public void new_item(View view) {
        Intent intent = new Intent(getApplicationContext(), NewEvent.class);
        intent.putExtra("userId", user.getUserId());
        intent.putExtra("isNew", true);

        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                eventList = db.getAllEvents(user.getFamilyId());
                adapter = new EventsAdapter(this, 0, 0, eventList);
                lv.setAdapter(adapter);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "user cancel", Toast.LENGTH_LONG).show();
            }
        }//and of request code -


    }


    }