package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class query_status extends Activity {

    String name;
    String courseId;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    private ProgressDialog pDialog;
    ListView lv;
    Spinner sp;
    SearchView s;
    SearchableAdapter la;
    String course_name[] = new String[100];
    static String studentId[] = new String[100];
    String studentIdf[] = new String[100];
    Double sts[] = new Double[100];
    List<String> studentData =new ArrayList<String>();

    String studentName;
    int a=0;
    int p=0;
    int l=0;
    int k1=0;
    double total=0;
    String attendanceStat[] = new String[100];
    EditText ed;
    String eid;
    String attendanceStatus=null;
    public static String s_id;
    // String course[] = {"asdf","asdfasdf"};
    int j=0,k=0,m=0,n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_status);
        courseId = CourseReport.c_id;

        lv=(ListView) findViewById(R.id.listStudent);
        sp = (Spinner) findViewById(R.id.spinner);

        List<Double> list = new ArrayList<Double>();
        list.add(Double.valueOf(90));
        list.add(Double.valueOf(80));
        list.add(Double.valueOf(75));
        list.add(Double.valueOf(70));
        list.add(Double.valueOf(65));
        list.add(Double.valueOf(50));
        ArrayAdapter<Double> dataAdapter = new ArrayAdapter<Double>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sp.setAdapter(dataAdapter);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setPrompt("Select your favorite Planet!");

        sp.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //c_id =(String) lv.getItemAtPosition(position);
                TextView textView = (TextView) view.findViewById(R.id.listText);
                s_id = textView.getText().toString();
                System.out.println(s_id);
                //Intent intent = new Intent(query_status.this,att_form.class);
                // startActivity(intent);
                // Toast.makeText(takeNote.this, "You Clicked at " +noteName[+ position], Toast.LENGTH_SHORT).show();
                Log.e("pass 8", "connection success ");
            }
        });

        new Select_students().execute();
        // new Select_status().execute();


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int b=0;b<j;b++)
                {
                    if(sts[b]>=Double.parseDouble(sp.getSelectedItem().toString()))
                    {

                        studentIdf[m]=studentId[b];
                        System.out.println("gayhfu"+studentIdf[m]);
                        m++;

                    }
                }
                lv.setAdapter(new listAdapter3(query_status.this,studentIdf, m));
                m=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // lv.setAdapter(new listAdapter3(query_status.this,studentId, j));
            }
        });



    }
    class Select_students extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(query_status.this);
//            pDialog.setMessage("Preparing the Student's List...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) { // TODO Auto-generated method stub // here Check for success tag
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("courseId", courseId));
            System.out.println(courseId);

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://attendancepro.host22.com/get_student1.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getApplicationContext(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();
            }

            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");

                }
                result = sb.toString();
                //System.out.println(result);
                int i=0;
                String s = "{\"studentId\":\"";
                result=result.replace(s,"");
                result=result.replace("\",\"","");
                result=result.replace("\"}","^");
                result=result.replace("attendanceStatus\":\"","$");

                System.out.println(result);
                String str = "";
                for(i=1;i<result.length();i++)
                {
                   // System.out.println(result.length());
                    if(result.charAt(i)=='$')
                    {
                        studentId[j] = str;
                        System.out.println(studentId[j]);
//                        studentData.add(studentId[j]);
                        j++;
                        str="";
                    }
                    else if(result.charAt(i)=='^')
                    {
                        attendanceStat[n]=str;
                        System.out.println("\n"+attendanceStat[n]);
                        n++;
                        str="";
                    }
                    else
                        str=str+result.charAt(i);
                }



                is.close();

                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }

            return null;
        }
        protected void onPostExecute(String message)
        {// pDialog.dismiss();
            lv.setAdapter(new listAdapter3(query_status.this,studentId, j));
            for(k1=0;k1<j;k1++) {
                attendanceStatus =  attendanceStat[k1];
                System.out.println("status"+attendanceStatus);
                for (k = 0; k < attendanceStatus.length() ; k++) {
                    if (attendanceStatus.charAt(k) == 'P')
                        p++;
                    else if (attendanceStatus.charAt(k) == 'A')
                        a++;
                    else
                        l++;
                }

                int lp = l / 3;
                System.out.println("statusP"+p);
                System.out.println("statusl"+l);
                System.out.println("statusa"+a);

                double v1 = (double) ((p + l - lp) * 100);
                System.out.println("status"+v1);
                double v2 = (double) (attendanceStatus.length() );
                System.out.println("status"+v2);
                //total = ((p+l-lp)*100)/(attendanceStatus.length()-1);
                // String parcentage = Double.toString()
                total = v1 / v2;
                System.out.println("status"+total);

                sts[k1] =  total;

                a=0;
                l=0;
                p=0;
                total =0;


            }


            if (message != null)
            { Toast.makeText(query_status.this, message, Toast.LENGTH_LONG).show(); }
        }


    }


}
