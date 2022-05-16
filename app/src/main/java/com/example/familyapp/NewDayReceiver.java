package com.example.familyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.familyapp.objects.Task;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NewDayReceiver extends BroadcastReceiver {



    OpenHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
        Toast.makeText(context, "NEW DAY", Toast.LENGTH_SHORT).show();
        System.out.println("NEW DAY");
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.getHour() != 0 || localDateTime.getMinute() > 2){
            System.out.println("WHAT");
            return;
        }
        int todayMonth = localDateTime.getMonthValue();  // 1 - 12

        System.out.println("<HELLLO>");

        db = new OpenHelper(context);



        int daytoday = localDateTime.getDayOfWeek().getValue() + 1;
        System.out.println(daytoday);


        ArrayList<Task> frecList = new ArrayList<Task>();
        db.open();

        frecList = db.getAllFrequencyTasks();
        db.close();

        for (int i = 0; i < frecList.size(); i++){
            Task curTask = frecList.get(i);

            if (Task.generateFrequency(curTask.getFrequency()) == 2){
                Task newT = new Task(curTask.getFamily_id(), curTask.getOwner_id(), 0, todayMonth, curTask.getDescription() +
                        " " + localDateTime.getDayOfMonth()+"/"+localDateTime.getMonthValue(), curTask.getPerformer_id(), Task.generateFrequency(1), 0);
                db.open();
                db.saveTask(newT);
                db.close();

            }
            if (Task.generateFrequency(curTask.getFrequency()) == 3 && curTask.getDay() == daytoday){
                Task newT = new Task(curTask.getFamily_id(), curTask.getOwner_id(), 0, todayMonth, curTask.getDescription() +
                        " " + localDateTime.getDayOfMonth()+"/"+localDateTime.getMonthValue(), curTask.getPerformer_id(), Task.generateFrequency(1), 0);
                db.open();
                db.saveTask(newT);
                db.close();
            }
        }



    }
}