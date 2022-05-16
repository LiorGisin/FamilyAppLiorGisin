package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp.objects.Task;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.example.familyapp.MainActivity.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static com.example.familyapp.objects.Task.EVERY_DAY;
import static com.example.familyapp.objects.Task.ONCE;
import static com.example.familyapp.objects.Task.ONCE_A_WEEK;

public class NewTask extends AppCompatActivity {

    HashMap<RadioButton, Task.FREQUENCY> btnFrequency = new HashMap<>();


    TextView description, titleTask;
    String performerName;
    int performer;
    RadioButton once, every_day, once_a_week;
    TextInputLayout textInputLayout;
    int day = 1;
    AutoCompleteTextView days, users;
    OpenHelper db;
    User user;
    long userId;
    Boolean isNew;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);



        db = new OpenHelper(this);
        description = findViewById(R.id.description);
        titleTask = findViewById(R.id.titleTask);
        once = findViewById(R.id.once);
        every_day = findViewById(R.id.every_day);
        once_a_week = findViewById(R.id.once_a_week);

        btnFrequency.put(once, Task.FREQUENCY.once);
        btnFrequency.put(every_day, Task.FREQUENCY.every_day);
        btnFrequency.put(once_a_week, Task.FREQUENCY.once_a_week);

        textInputLayout = findViewById(R.id.menu_dropdown);
        days = findViewById(R.id.drop_items);
        users = findViewById(R.id.drop_itemsUsers);


        String [] days = {"Sunday","Monday","Tuesday","Wednesday", "Thursday","friday", "Saturday"};
        ArrayAdapter<CharSequence> itemAdapter = new ArrayAdapter<>(NewTask.this, R.layout.support_simple_spinner_dropdown_item,days);
        itemAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.days.setAdapter(itemAdapter);
        this.days.setText(this.days.getAdapter().getItem(0).toString(),false);
        this.days.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                day = position + 1;
            }
        });

        Intent intent = getIntent();
        userId = intent.getExtras().getLong("userId");
        String _description = intent.getExtras().getString("description");
        long performerId = intent.getExtras().getLong("performer");
        isNew = intent.getExtras().getBoolean("isNew");
        int freqency = intent.getExtras().getInt("frec");

        db.open();
        user = db.findUser(userId);
        db.close();


        String [] allUsers = db.getAllUsersNames(user.getFamilyId());
        ArrayAdapter<CharSequence> itemAdapter2 = new ArrayAdapter<>(NewTask.this, R.layout.support_simple_spinner_dropdown_item,allUsers);
        itemAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.users.setAdapter(itemAdapter2);
        this.users.setText(this.users.getAdapter().getItem(0).toString(),false);
        this.users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                performerName = allUsers[position];
                System.out.println(performerName);
                performer = position;
            }
        });

        // default value
        performer = 0;
        performerName = allUsers[performer];






            if (!isNew) {
                titleTask.setText("Edit your task");
                description.setText(_description);
                this.users.setText(this.users.getAdapter().getItem(getNumOfPer(db.getUserName(performerId))).toString(),false);

                if (freqency == 1){
                    once.setChecked(true);
                }
                else if (freqency == 2){
                    every_day.setChecked(true);
                }
                else{
                    once_a_week.setChecked(true);
                    this.days.setVisibility(View.VISIBLE);
                    textInputLayout.setVisibility(View.VISIBLE);
                    int day = intent.getExtras().getInt("day");
                    this.days.setText(this.days.getAdapter().getItem(day-1).toString(),false);
                }

            }







        }



    public int getNumOfPer(String p) {
        String [] allUsers= db.getAllUsersNames(user.getFamilyId());
            for (int i = 0; i < allUsers.length; i++) {

                if (allUsers[i].equals(p)) {
                    return i;
                }

            }

        return -1;

    }



    public void once_a_week(View view) {
        days.setVisibility(View.VISIBLE);
        textInputLayout.setVisibility(View.VISIBLE);

    }

    public void every_day(View view) {
        days.setVisibility(View.INVISIBLE);
        textInputLayout.setVisibility(View.INVISIBLE);
        day=0;
    }


    public void only_once(View view) {
        days.setVisibility(View.INVISIBLE);
        textInputLayout.setVisibility(View.INVISIBLE);
        day=0;
    }


    public void save(View view) {

        LocalDateTime localDateTime = LocalDateTime.now();
        int todayMonth = localDateTime.getMonthValue();  // 1 - 12

        if (description.getText().toString().length()>0 && performerName != null)
        {
            if(!once.isChecked() && !every_day.isChecked() && !once_a_week.isChecked()){
                Toast.makeText(this,"choose frec", Toast.LENGTH_LONG).show();
                return;
            }

            else{
                User u = db.exist(performerName, user.getFamilyId());

                if (u != null) {
                    int f;
                    if (once.isChecked()) {
                        f = ONCE;
                    } else if (every_day.isChecked()) {
                        f = EVERY_DAY;
                    } else {
                        f = ONCE_A_WEEK;
                    }

                    User owner = db.findUser(userId);
                    Task t = new Task(owner.getFamilyId(), userId, 0, todayMonth, description.getText().toString(), db.return_user_id(u.getFirstName(), u.getPassword()), Task.generateFrequency(f), day);


                    Task tCopy = t.copyConstructor();

                    if (Task.generateFrequency(t.getFrequency()) == 2){
                        db.open();
                        tCopy.setFrequency(Task.generateFrequency(1));
                        tCopy.setDescription(t.getDescription() + " " + LocalDateTime.now().getDayOfMonth() +"/"+LocalDateTime.now().getMonthValue());
                        db.saveTask(tCopy);
                        db.close();
                        System.out.println("bye");

                    }
                    else if (Task.generateFrequency(t.getFrequency()) == 3 && localDateTime.getDayOfWeek().getValue() + 1 == t.getDay()){
                        db.open();
                        tCopy.setFrequency(Task.generateFrequency(1));
                        tCopy.setDescription(t.getDescription() + " " + LocalDateTime.now().getDayOfMonth() +"/"+LocalDateTime.now().getMonthValue());
                        db.saveTask(tCopy);
                        db.close();
                        System.out.println("hi");
                    }

                     
                    if (isNew) {
                        db.open();
                        t = db.saveTask(t);
                        db.close();
                        System.out.println("HERE");

                        sendSMSMessage(db.findUser(t.getPerformer_id()).getPhoneNumber(), "FamilyApp: you have a new task from " + owner.getFirstName()+"!");
                        Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        long id = getIntent().getExtras().getLong("taskId");
                        t.setId(id);
                        db.open();
                        db.updateByRow(t);
                        System.out.println("fnriegh");
                        db.close();
                    }

                    Toast.makeText(this, "data saved", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent();
                    intent.putExtra("ownerId", String.valueOf(owner.getUserId()));
                    intent.putExtra("description", description.getText().toString());
                    intent.putExtra("performerName", performerName);
                    intent.putExtra("isNew", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(this,"user not exsist", Toast.LENGTH_LONG).show();
                }
                }


            }
        else {
            Toast.makeText(this,"pls fill all filds", Toast.LENGTH_LONG).show();
        }


        }






    public void cancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    protected void sendSMSMessage(String phoneNo, String message) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            sendSMS(phoneNo, message);
        }
    }

    private void sendSMS(String phoneNo, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }



}