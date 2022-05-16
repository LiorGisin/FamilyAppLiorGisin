package com.example.familyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familyapp.objects.Event;
import com.example.familyapp.objects.User;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<Event> {

    Context context;
    List<Event> objects;

    public EventsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Event> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity__layout_events,parent,false);


        TextView eName = (TextView)view.findViewById(R.id.eName);
        TextView eDate = (TextView)view.findViewById(R.id.eDate);
        TextView eTime = (TextView)view.findViewById(R.id.eTime);



        Event temp = objects.get(position);
        String sHour = ""+temp.getHour();
        String sMinute = ""+temp.getMinutes();

        if (temp.getHour() < 10){
            sHour = "0"+temp.getHour();
        }

        if (temp.getMinutes() < 10){
            sMinute = "0"+temp.getMinutes();
        }

        eName.setText(temp.getDescription());
        eDate.setText("on "+ temp.getDay() + "/" + temp.getMonth() + "/" + temp.getYear());
        eTime.setText("at "+ sHour + ":" + sMinute);


        return view;
    }

}