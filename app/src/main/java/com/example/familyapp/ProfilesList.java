package com.example.familyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.familyapp.objects.Family;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.util.ArrayList;

public class ProfilesList extends AppCompatActivity {

    ListView lv;
    ArrayList<User> usersList;
    UsersAdapter adapter;
    User lastSelected;
    OpenHelper db;
    TextView title;
    long userId;
    User user;
    Family family;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles_list);


        Intent intent = getIntent();
        userId = intent.getExtras().getLong("userId");

        title = findViewById(R.id.titleFam);
        lv = findViewById(R.id.lv3);
        db = new OpenHelper(this);
        db.open();
        user = db.findUser(userId);
        usersList = db.getAllUsers(user.getFamilyId());
        family = db.findFamily(user.getFamilyId());
        db.close();
        adapter = new UsersAdapter(this, 0, 0, usersList);
        lv.setAdapter(adapter);

        title.setText(family.getName()+" family");



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelected = adapter.getItem(position);

                if (lastSelected.getUserId() == userId) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                    intent.putExtra("userId", lastSelected.getUserId());
                    intent.putExtra("theSame", true);

                    startActivityForResult(intent, 0);
                }

                else{
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                    intent.putExtra("userId", lastSelected.getUserId());
                    intent.putExtra("theSame", false);

                    startActivityForResult(intent, 0);
                }
            }
        });



    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
        {
            if(resultCode==RESULT_OK) {
                usersList = db.getAllUsers(user.getFamilyId());
                adapter = new UsersAdapter(this, 0, 0, usersList);
                lv.setAdapter(adapter);
            }

        }
    }
}
