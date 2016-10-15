package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by rafi on 6/25/15.
 */
public class CourseReport extends Activity {

    String name;
    String id;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    private ProgressDialog pDialog;
    ListView lv;
    String course_name[] = new String[100];
    String course_title[] = new String[100];
    public static String c_id;
    String course[] = {"asdf","asdfasdf"};
    int j=0,y=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_course_report);
        id = login.t_id;

        lv=(ListView) findViewById(R.id.listView);
       // lv.setAdapter(new listAdapter(CourseReport.this, course_name, j));





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //c_id =(String) lv.getItemAtPosition(position);
                TextView textView = (TextView) view.findViewById(R.id.listsubText);
                c_id = textView.getText().toString();
                System.out.println(c_id);
                Intent intent = new Intent(CourseReport.this,StudentId.class);
                startActivity(intent);
                // Toast.makeText(takeNote.this, "You Clicked at " +noteName[+ position], Toast.LENGTH_SHORT).show();
                Log.e("pass 8", "conysttanection success ");
            }
        });

        new Select_course().execute();
        //lv.setAdapter(new listAdapter(Take_att.this, course, 2));

    }
    class Select_course extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CourseReport.this);
            pDialog.setMessage("Getting Course Information....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) { // TODO Auto-generated method stub // here Check for success tag
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("id", id));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://attendancepro.host22.com/course_select.php");
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
                System.out.println(result);
                int i=0;
                String s = "{\"courseId\":\"";
                result=result.replace("courseName","");
                result=result.replace(s,"");
                result=result.replace("\",\"","^");
                result=result.replace("\":\"","");
                result=result.replace("\"}","$");
                System.out.println("adakfja"+result);
                String str = "";
                for(i=1;i<result.length();i++)
                {
                    if(result.charAt(i)=='$')
                    {
                        course_name[j] = str;

                        System.out.println("id"+course_name[j]);

                        j++;
                        str="";
                    }
                    else if(result.charAt(i)=='^')
                    {
                        course_title[y] = str;

                        System.out.println("name"+course_title[j]);

                        y++;
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
        { pDialog.dismiss();
            int i;
            for(i=0;i<j;i++)
            {
                System.out.println(course_name[i]);
            }
            lv.setAdapter(new listAdapter(CourseReport.this,course_name,course_title, j));

            if (message != null)
            { Toast.makeText(CourseReport.this, message, Toast.LENGTH_LONG).show(); }
        }


    }
}
