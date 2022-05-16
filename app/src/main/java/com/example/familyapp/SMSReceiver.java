package com.example.familyapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.familyapp.R;

public class SMSReceiver extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    private final int NOTIFICATION_CANCEL_CODE = 0;
    private final String NOTIFICATION_CHANNEL_ID = "FammilyAppChannel";

    NotificationManagerCompat notificationManager;
    NotificationChannel channel;
    NotificationManager manager;

    Context context;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!= null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.d("SmsReceiver","test");
            // return;
        } else {
            return;
        }
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        notificationManager = NotificationManagerCompat.from(context);
        this.context = context;
        Log.d("SmsReceiver","test");

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    // Show alert
                    Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, Toast.LENGTH_LONG).show();
                    if (message.startsWith("FamilyApp")){
                        Log.d("SmsReceiver", "message from myAPP!!!");
                        makeNotification("FamilyApp",message);
                        Toast.makeText(context, "message from myAPP!!!", Toast.LENGTH_LONG).show();

                    }
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);


        }
    }

    private void makeNotification(String notiTitle, String notiText) {

        System.out.println("" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            createNotificationChannel();

            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);

            Notification.Builder notificationB = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(notiTitle)
                    .setContentText(notiText)
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(largeIcon)
                    .setAutoCancel(true);

            manager.notify(NOTIFICATION_CANCEL_CODE, notificationB.build());
        } else {
            notifyBeforAPI26(notiTitle, notiText);
        }
    }
    private void notifyBeforAPI26(String notiTitle, String notiText) {
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // before API 26
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        //build notification data:
        builder.setContentIntent(contentIntent);
        //   builder.setSmallIcon(R.drawable.stars);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiText);
        // builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.kiss));
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setWhen(System.currentTimeMillis());
        Notification noti = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_CANCEL_CODE, noti);

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription(context.getString(R.string.app_name));
            channel.setLightColor(Color.GREEN);

            manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}