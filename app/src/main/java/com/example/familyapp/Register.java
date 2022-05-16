package com.example.familyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;


public class Register extends AppCompatActivity {


    EditText first_name, private_password, family_name, family_password, phoneNumber;
    Bitmap bitmap, image;
    ImageButton ibMain;
    Button register;
    OpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first_name = findViewById(R.id.first_name);
        private_password = findViewById(R.id.private_password);
        family_name = findViewById(R.id.family_name);
        family_password = findViewById(R.id.family_password);
        phoneNumber = findViewById(R.id.phone_number);
        ibMain = findViewById(R.id.ibMain);

        image = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic);

        db = new OpenHelper(this);


    }

    public void register(View view) {
        if (first_name.getText().toString().length()>0 && private_password.getText().toString().length()>0 && family_name.getText().toString().length()>0 && family_password.getText().toString().length()>0 && phoneNumber.getText().toString().length()>0){
            String name = first_name.getText().toString();
            String privatepassword = private_password.getText().toString();
            String familyName = family_name.getText().toString();
            String familypassword = family_password.getText().toString();
            String phoneNum = phoneNumber.getText().toString();


            long familyId = db.findFamily(familyName, familypassword);

            if (familyId >-1){
                if (db.userExsintInTheFamily(familyId, name)){
                    Toast.makeText(this,name +" is exsist in this family already", Toast.LENGTH_LONG).show();
                    return;
                }
                if (db.userExist(name, privatepassword)){
                    Toast.makeText(this,"choose another pass", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(this,"good", Toast.LENGTH_LONG).show();
                User user = new User (name, privatepassword, familyId, ImageHandler.bitmapToString(image), phoneNum);
                db.open();
                user = db.saveUser(user);
                db.close();


                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.putExtra("userId", user.getUserId());

                startActivity(intent);

            }
            else{
                Toast.makeText(this,"family not exist", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void capture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ibMain.setBackgroundResource(0);
            ibMain.setImageBitmap(bitmap);
            image = bitmap;
        }
    }
}