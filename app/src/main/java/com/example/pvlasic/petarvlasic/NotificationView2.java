package com.example.pvlasic.petarvlasic;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by pvlasic on 1/26/18.
 */

public class NotificationView2 extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification2);

        //---look up the notification manager service---
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        //---cancel the notification that we started---
        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }
}
