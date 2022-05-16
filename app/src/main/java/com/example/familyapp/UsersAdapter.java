package com.example.familyapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.time.LocalDateTime;
import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    Context context;
    List<User> objects;
    OpenHelper db;
    String [] months= {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public UsersAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<User> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        db = new OpenHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.users_layout,parent,false);

        LocalDateTime localDateTime = LocalDateTime.now();
        int curMonth = localDateTime.getMonthValue();  // 1 - 12

        String currentMonth = months[curMonth-1];


        TextView uName = (TextView)view.findViewById(R.id.uName);
        ImageView uPic = (ImageView) view.findViewById(R.id.uPic);
        TextView uPoints = (TextView) view.findViewById(R.id.uPoints);


        User temp = objects.get(position);


        uPic.setImageBitmap(ImageHandler.stringToBitmap(temp.getImage()));
        uName.setText(temp.getFirstName());
        uPoints.setText(currentMonth + " score: "+db.getNumOfPoints(temp.getUserId()));
        
        return view;
    }



}
