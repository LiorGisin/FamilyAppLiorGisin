package com.example.familyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.time.LocalDateTime;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {


    Button btnPic, btnSave, btnCancel;
    TextView tvName, tvPoints;
    User u;
    Boolean theSame;

    ImageView userImage;
    Bitmap bitmap;
    long userId;
    String [] months= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    OpenHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnPic = findViewById(R.id.btnPic);
        tvName = findViewById(R.id.tvName);
        tvPoints = findViewById(R.id.tvPoints);
        userImage = findViewById(R.id.userImage);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnPic.setOnClickListener(this);


        db = new OpenHelper(this);

        Intent intent = getIntent();
        userId = intent.getExtras().getLong("userId");
        theSame = intent.getExtras().getBoolean("theSame");


        if (theSame == false){
            btnPic.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.INVISIBLE);
            btnCancel.setText("back");
        }


        LocalDateTime localDateTime = LocalDateTime.now();
        int curMonth = localDateTime.getMonthValue();  // 1 - 12

        String currentMonth = months[curMonth-1];

        db.open();
        u = db.findUser(userId);
        db.close();

        tvName.setText(u.getFirstName());
        int points = db.getNumOfPoints(u.getUserId());
        tvPoints.setText(currentMonth + " score: "+points);

        bitmap = ImageHandler.stringToBitmap(u.getImage());
        userImage.setImageBitmap(bitmap);


    }

    public void onClick(View view) {
        if(btnPic==view)
        {
            System.out.println("fghfg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
        else if(btnSave==view)
        {
            u.setImage(ImageHandler.bitmapToString(bitmap));
            db.open();
            db.updateImage(u);
            db.close();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (btnCancel==view)
        {
            setResult(RESULT_CANCELED,null);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
        {
            if(resultCode==RESULT_OK)
            {
                bitmap = (Bitmap)data.getExtras().get("data");
                if(bitmap!=null)
                    userImage.setImageBitmap(bitmap);
            }
        }
    }
}

