package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class summary_report extends Activity {

    double status[] = new double[100];
    String sid[] = new String[100];
    String cid;
    //int j=0;
    int x;
    ListView lv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

//        status=StudentId.sts;
//        sid = StudentId.studentId;
//        x=StudentId.j;
        cid =CourseReport.c_id;
        System.out.println("sfnajaaifh"+cid);
        tv= (TextView) findViewById(R.id.summ_name);
        tv.setText(cid);
        System.out.println("afjhafjh "+status[1]);
        for(int i=0;i<x;i++)
        {
            System.out.println("da"+status[i]);
            System.out.println("ad"+sid[i]);
        }

        lv = (ListView) findViewById(R.id.listView2);

        //lv.setAdapter(new ListAdapterP(summary_report.this,sid,status,x));

        System.out.println("SLda"+x);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary_report, menu);
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
