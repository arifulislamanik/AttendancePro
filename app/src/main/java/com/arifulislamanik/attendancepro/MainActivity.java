package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    public static final int seconds=5;
    public static final int milisecond=seconds*1000;
    public static final int delay=2;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        pb.setMax(max_pro());
        f1();
    }
    public void f1()
    {
        new CountDownTimer(milisecond,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {

                pb.setProgress(establish_progress(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                Intent x = new Intent(MainActivity.this,login.class);
                startActivity(x);
                finish();
            }
        }.start();

    }
    public int establish_progress(long miliseconds)
    {
        return (int)((milisecond-miliseconds)/1000);
    }
    public int max_pro()
    {
        return seconds-delay;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
