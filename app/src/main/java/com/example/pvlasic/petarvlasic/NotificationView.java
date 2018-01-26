package com.example.pvlasic.petarvlasic;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by pvlasic on 1/26/18.
 */

public class NotificationView extends Activity{
    int notificationID = 2;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        //---look up the notification manager service---
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        //---cancel the notification that we started---
        nm.cancel(getIntent().getExtras().getInt("notificationID"));

        displayNotification2();

    }
    protected void displayNotification2()
    {
        //---PendingIntent to launch activity if the user selects
        // this notification---
        Intent i = new Intent(this, NotificationView2.class);

        i.putExtra("notificationID", notificationID);


        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, i, 0);

        long[] vibrate = new long[] { 100, 250, 100, 500};

//Notification Channel - novo od Android O

        String NOTIFICATION_CHANNEL_ID = "my_channel_01";
        CharSequence channelName = "hr.math.karga.MYNOTIF";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(vibrate);


        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);



        nm.createNotificationChannel(notificationChannel);

        Notification notif = new Notification.Builder(this)
                .setTicker("Izvoli notifikaciju 2")
                .setContentTitle("Notifikacija 2")
                .setContentText("Ugodan dan želim :)")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setContentIntent(pendingIntent)
                .setVibrate(vibrate)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .build();






        nm.notify(notificationID, notif);
    }

}
