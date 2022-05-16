package com.example.familyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp.objects.Family;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

import java.time.LocalDateTime;
import java.util.Calendar;

public class HomeScreen extends AppCompatActivity {

    long userId;
    Button shopping, tasks, events;
    User u;
    TextView title, familyName;

    String winner;
    Family family;

    OpenHelper db;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        shopping = findViewById(R.id.shopping);
        tasks = findViewById(R.id.tasks);
        events = findViewById(R.id.events);
        title = findViewById(R.id.title);
        familyName = findViewById(R.id.familyName);
        db = new OpenHelper(this);

        Intent intent = getIntent();
        userId = intent.getExtras().getLong("userId");

        u = db.findUser(userId);
        System.out.println(u.getFamilyId());
        family = db.findFamily(u.getFamilyId());



        title.setText("Welcome " + u.getFirstName()+"!");
        familyName.setText(family.getName() + " family");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        
        sp = getSharedPreferences("userlogins", MODE_PRIVATE);

        int lastMonth = sp.getInt(userId+"", -1);
        if (lastMonth == -1){
            Toast.makeText(this, "Welcome for the first time!", Toast.LENGTH_SHORT).show();

        } else {
            if (lastMonth != LocalDateTime.now().getMonthValue()){
                db.open();
                winner = db.newMonth(u.getFamilyId());
                db.close();
                builder.setTitle("the winner of last month is " + winner + "!");
                builder.setMessage("");
                builder.setCancelable(true);
                builder.setPositiveButton("ok", new HomeScreen.HandleAlertDialogListener());
                //dialog.getWindow().setBackgroundDrawableResource(R.drawable.logo);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(userId+"", LocalDateTime.now().getMonthValue());
        editor.commit();



    }





    public void onClick(View view) {

        if(view == shopping) {

            Intent intent = new Intent(getApplicationContext(), ItemsActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("item", "shopping");

            startActivity(intent);

        }

        if(view == tasks) {

            Intent intent = new Intent(getApplicationContext(), ItemsActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("item", "tasks");

            startActivity(intent);

        }

        if (view == events){

            Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);

        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.users_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.my_profile){
            Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
            intent.putExtra("userId", userId);
            intent.putExtra("theSame", true);
            startActivityForResult(intent, 0);
        }
        else if (item.getItemId()==R.id.everyoneProfile)  {

            Intent intent = new Intent(getApplicationContext(), ProfilesList.class);
            intent.putExtra("userId", userId);
            startActivity(intent);

        }
        return true;
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
        {
            if(resultCode==RESULT_OK) {

            }

            else{

            }
        }
    }

    /*private void doAlarm() {
        Calendar c = Calendar.getInstance();
        // c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        //c.set(Calendar.HOUR_OF_DAY, 0);
        // c.set(Calendar.SECOND, 0);

/*        Intent intent = new Intent(MainActivity.this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,
                0, intent, 0);
*/

      /*  Intent intent = new Intent(HomeScreen.this, NewDayReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeScreen.this, 1, intent, 0);

        AlarmManager alma = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alma.setRepeating(AlarmManager.RTC_WAKEUP,  c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

       */


//AlarmManager.INTERVAL_DAY


    public  class  HandleAlertDialogListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }




}