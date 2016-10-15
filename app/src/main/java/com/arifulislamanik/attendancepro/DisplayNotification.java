package com.arifulislamanik.attendancepro;

/**
 * Created by ariful islam anik on 8/30/2015.
 */
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class DisplayNotification extends Activity {
    /** Called when the activity is first created. */
    static final String time = SetAlarm.s3;
    String t = login.t_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //---get the notification ID for the notification;
        // passed in by the MainActivity---
        int notifID = getIntent().getExtras().getInt("NotifID");

        System.out.println("st" + time);

        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent i = new Intent("com.arifulislamanik.attendancepro.AlarmDetails");
        i.putExtra("NotifID", notifID);

        PendingIntent detailsIntent =
                PendingIntent.getActivity(this, 0, i, 0);

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Notification notif = new Notification(
                R.drawable.app_icon,
                "Class Reminder!",
                System.currentTimeMillis());
        // Uri alarm = RingtoneManager.getDefaultType(RingtoneManager.TYPE_NOTIFICATION);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        CharSequence from = "Class Reminder";
        CharSequence message = "Hello Sir!! You Have a Scheduled class Now!!!!!! ";
        //+ time;
        notif.setLatestEventInfo(this, from, message, detailsIntent);

        //---100ms delay, vibrate for 250ms, pause for 100 ms and
        // then vibrate for 500ms---
        notif.vibrate = new long[] { 100, 250, 100, 500};
        notif.sound = alarmSound;
        nm.notify(notifID, notif);
        //---destroy the activity---
        finish();
    }
}
