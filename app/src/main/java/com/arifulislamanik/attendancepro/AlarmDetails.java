package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/**
 * Created by ariful islam anik on 8/30/2015.
 */
public class AlarmDetails extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_details);

        //---look up the notification manager service---
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        //---cancel the notification---
        nm.cancel(getIntent().getExtras().getInt("NotifID"));
    }
}