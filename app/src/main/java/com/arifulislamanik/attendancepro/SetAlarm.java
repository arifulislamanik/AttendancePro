package com.arifulislamanik.attendancepro;

/**
 * Created by ariful islam anik on 8/30/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import java.util.Calendar;
import java.util.Timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlarm extends Activity {
    TimePicker timePicker;
    DatePicker datePicker;
    public static String s3 = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm);

        //---Button view---
        Button btnOpen = (Button) findViewById(R.id.btnSetAlarm);
        btnOpen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                timePicker = (TimePicker) findViewById(R.id.timePicker);
                datePicker = (DatePicker) findViewById(R.id.datePicker);

                //---use the AlarmManager to trigger an alarm---
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                //---get current date and time---
                Calendar calendar = Calendar.getInstance();

                //---sets the time for the alarm to trigger---
                calendar.set(Calendar.YEAR, datePicker.getYear());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                calendar.set(Calendar.SECOND, 0);

                //---PendingIntent to launch activity when the alarm triggers---
                Intent i = new Intent("com.arifulislamanik.attendancepro.DisplayNotification");


                String s1 = timePicker.getCurrentHour().toString();
                String s2 = timePicker.getCurrentMinute().toString();
                s3 = s1+":"+s2;
                System.out.println("curTime"+s3);
                //---assign an ID of 1---
                i.putExtra("NotifID", 1);
                i.putExtra("time",s3);




                PendingIntent displayIntent = PendingIntent.getActivity(
                        getBaseContext(), 0, i, 0);

                //---sets the alarm to trigger---
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), displayIntent);

                Toast.makeText(getBaseContext(), "Alarm is set successfully", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }
}
