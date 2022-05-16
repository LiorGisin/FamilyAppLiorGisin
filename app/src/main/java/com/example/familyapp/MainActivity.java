package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    public static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        receiveSMSPermission();
        aNewDay();

        BroadcastReceiver br = new SMSReceiver();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(br,filter);
    }


    public void log_in(View view) {
        startActivity(new Intent(this,log_in.class));
    }

    public void sing_up(View view) {
        startActivity(new Intent(this,Register.class));

    }

    public void new_family(View view) {
        startActivity(new Intent(this, Create_new_family.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
           case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "receive SMS permittion granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "receive SMS permittion granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);


    }

    private void receiveSMSPermission(){


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
    }

    // c.setTimeInMillis(System.currentTimeMillis());
    //c.set(Calendar.HOUR_OF_DAY, 0);
    // c.set(Calendar.SECOND, 0);

/*        Intent intent = new Intent(MainActivity.this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,
                0, intent, 0);
*/

    private void aNewDay() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(MainActivity.this, NewDayReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);

        AlarmManager alma = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alma.setRepeating(AlarmManager.RTC_WAKEUP,  c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}