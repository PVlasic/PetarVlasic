package com.example.pvlasic.petarvlasic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBAdapter db = new DBAdapter(this);
    int notificationID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayNotification1();
    }

    public void createTables(View view) {

        db.open();

        db.insertLik("Mickey", "Walt Disney");
        db.insertLik("Rambo", "David Morrel");
        db.insertLik("Rocky", "Sylvester Stalone");
        db.insertLik("Batman", "Bob Kane");
        db.insertLik("Iron man", "Stan Lee");

        db.insertFilm("5");
        db.insertFilm("4");
        db.insertFilm("3");
        db.insertFilm("1");
        db.insertFilm("2");

        db.close();
        Toast.makeText(this, "Baza kreirana", Toast.LENGTH_SHORT).show();

    }

    public void printDB(View view) {
        TextView textView = (TextView) findViewById(R.id.likovi);
        textView.setText("");
        textView = (TextView) findViewById(R.id.filmovi);
        textView.setText("");

        db.open();

        Cursor c = db.GetAllCharacters();
        if (c.moveToFirst())
        {
            do {
                DisplayLik(c);
            } while (c.moveToNext());
        }
        c = db.GetAllFilm();
        if (c.moveToFirst())
        {
            do {
                DisplayFilm(c);
            } while (c.moveToNext());
        }

        db.close();
    }

    public void DisplayLik(Cursor c)
    {
        TextView textView = (TextView) findViewById(R.id.likovi);

        textView.append("ID lika: " + c.getString(0) + "  Ime: " + c.getString(1) + "  Autor:" + c.getString(2) + "\n");
    }

    public void DisplayFilm(Cursor c)
    {
        TextView textView = (TextView) findViewById(R.id.filmovi);

        textView.append("ID filma: " + c.getString(0) + "  ID lika: " + c.getString(1) + "\n");
    }

    public void printMickey(View view) {
        EditText editText = (EditText) findViewById(R.id.mickey) ;
        db.open();
        Cursor c = db.GetAllCharacters();
        if (c.moveToFirst())
        {
            do {
                if(c.getString(1).equals("Mickey"))
                {
                    editText.setText(c.getString(2));
                }

            } while (c.moveToNext());
        }
        db.close();
    }

    public void _delete(View view) {
        db.open();
        if (db.deleteLik(1))
            Toast.makeText(this, "Uspješno obrisan lik", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Brisanje lika nije uspjelo", Toast.LENGTH_LONG).show();


        if (db.deleteFilm(1))
            Toast.makeText(this, "Uspješno obrisan film", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Brisanje filma nije uspjelo", Toast.LENGTH_LONG).show();

        db.close();
    }
    //ovo sam napravio dodatno da lakše testiram program
    public void deleteDB(View view) {
        this.deleteDatabase("MyDB");
    }

    protected void displayNotification1()
    {
        //---PendingIntent to launch activity if the user selects
        // this notification---
        Intent i = new Intent(this, NotificationView.class);

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

//za sve verzije
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

// za Notification Chanel

        nm.createNotificationChannel(notificationChannel);




//ovako je i u starim verzijama, jedino dodano .setChannelId (za stare verzije to brisemo)

        Notification notif = new Notification.Builder(this)
                .setTicker("Izvoli notifikaciju 1")
                .setContentTitle("Notifikacija 1")
                .setContentText("Klikni me")
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
