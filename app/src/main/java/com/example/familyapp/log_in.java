package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;


public class log_in extends AppCompatActivity {

    EditText user_name, password;
    TextView notCorrect;
    OpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        notCorrect = findViewById(R.id.notCorrect);



        db = new OpenHelper(this);



    }

    public void submit(View view){

        String username = user_name.getText().toString();
        String password_ = password.getText().toString();

        db.open();
        long id = db.return_user_id(username, password_);
        db.close();

        if (id != -1){
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            intent.putExtra("userId", id);
            startActivity(intent);
        }

        else{
            notCorrect.setVisibility(View.VISIBLE);
        }


    }


    

    public void new_user(View view) {
    }
}