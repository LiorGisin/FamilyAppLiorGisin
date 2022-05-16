package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familyapp.objects.Family;
import com.example.familyapp.openhelpers.OpenHelper;

public class Create_new_family extends AppCompatActivity {

    EditText family_name, family_password;
    OpenHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_family);

        family_name = findViewById(R.id.family_name);
        family_password = findViewById(R.id.family_password);

        database = new OpenHelper(this);

    }

    public void create_new_family(View view) {



        if (family_name.getText().toString().length()>0 && family_password.getText().toString().length()>0){

            String name = family_name.getText().toString();
            String password = family_password.getText().toString();

            if (database.familyExist(name, password)){
                Toast.makeText(this,"choose another password!", Toast.LENGTH_LONG).show();
                return;
            }
            else {
                Family family = new Family(name, password);
                database.open();
                family = database.saveFamily(family);
                database.close();
                Toast.makeText(this, "created!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this,"please fill all fields", Toast.LENGTH_LONG).show();
        }

    }
}