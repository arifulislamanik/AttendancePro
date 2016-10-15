package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by rafi on 6/25/15.
 */
public class Students_attendance extends Activity {

    String name;
    //String id;
    InputStream is=null;
    String result=null;
    String line=null;
    String courseId;
    int code;
    private ProgressDialog pDialog;
    ListView lv;
    String studentId[] = new String[100];
    public static String att="P";
    int i1,i2,i3;
    Button submit,selectDate;
    String getDay;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;
    public  int year,month,day,hour,minute;
    private int mYear, mMonth, mDay,mHour,mMinute;


    public Students_attendance()
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }


    String course[] = {"asdf","asdfasdf"};
    int j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_attendance);
        courseId = Take_att.c_id;

        i1=R.drawable.present;
        i2=R.drawable.absent_ns;
        i3=R.drawable.late_ns;
        lv=(ListView) findViewById(R.id.listView_student);
        lv.setAdapter(new StudentListAdapter(Students_attendance.this, studentId, att, j));

        selectDate = (Button) findViewById(R.id.dateSelect);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        getDay = df.format(c.getTime()).toUpperCase();


        selectDate.setText(getDay);

        System.out.println("CHECK"+getDay);
        getDay = getDay+'$';
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        submit = (Button) findViewById(R.id.submitButton);
        submit.setVisibility(View.INVISIBLE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String check = (String) selectDate.getText();



                    AlertDialog.Builder builder = new AlertDialog.Builder(Students_attendance.this);
                    builder.setMessage("Are you sure to submit??");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.out.println(att);

                                    new send_attendance().execute();
                                }
                            }
                    );
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


            }
        });


//        lv.setOnItemLongClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Intent intent = new Intent(Students_attendance.this,att_form.class);
//                startActivity(intent);
//                // Toast.makeText(takeNote.this, "You Clicked at " +noteName[+ position], Toast.LENGTH_SHORT).show();
//                Log.e("pass 8", "conysttanection success ");
//            }
//        });

//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(Students_attendance.this,att_form.class));
//
//                return true;
//            }
//        });



        new Select_course().execute();
        //lv.setAdapter(new listAdapter(Take_att.this, course, 2));

    }

    class send_attendance extends AsyncTask<String, String, String>{

        boolean failure = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Students_attendance.this);
            pDialog.setMessage("Storing the attendance Info...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) { // TODO Auto-generated method stub // here Check for success tag
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("attString", att));
            nameValuePairs.add(new BasicNameValuePair("courseId", courseId));
            nameValuePairs.add(new BasicNameValuePair("dateString", getDay));

//            for(int i=0;i<studentId.length;i++)
//            {
//                nameValuePairs.add(new BasicNameValuePair("studentId[]", studentId[i]));
//            }
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://attendancepro.host22.com/send_data.php");
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


            return null;
        }
        protected void onPostExecute(String message)
        { pDialog.dismiss();

            for(int i = 1; i<j;i++)
            {
                att=att+"P";
            }
            System.out.println(att);
            lv.setAdapter(new StudentListAdapter(Students_attendance.this, studentId,att,j));

            if (message != null)
            { Toast.makeText(Students_attendance.this, message, Toast.LENGTH_LONG).show(); }

            finish();
        }


    }

    class Select_course extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Students_attendance.this);
            pDialog.setMessage("Preparing the Student's List...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) { // TODO Auto-generated method stub // here Check for success tag
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("courseId", courseId));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://attendancepro.host22.com/get_student.php");
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
                String s = "{\"studentId\":\"";
                result=result.replace(s,"");
                result=result.replace("\":\"","");
                result=result.replace("\"}","$");
                //System.out.println(result);
                String str = "";
                for(i=1;i<result.length();i++)
                {
                    if(result.charAt(i)=='$')
                    {
                        studentId[j] = str;
                        System.out.println(studentId[j]);

                        j++;
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
            for(int i = 1; i<j;i++)
            {
                att=att+"P";
            }
            System.out.println(att);
            lv.setAdapter(new StudentListAdapter(Students_attendance.this, studentId,att,j));
            submit.setVisibility(View.VISIBLE);
//            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent i = new Intent(Students_attendance.this,att_form.class);
//                    i.putExtra("id",studentId[position]);
//                    startActivity(new Intent(Students_attendance.this,att_form.class));
//                    return false;
//                }
//            });
            if (message != null)
            { Toast.makeText(Students_attendance.this, message, Toast.LENGTH_LONG).show(); }
        }


    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    String mon = null;
                    String tday = null;
                    year = yearSelected;
                    month = monthOfYear+1;
                    day = dayOfMonth;

                    if(month==1)mon="JAN";
                    if(month==2)mon="FEB";
                    if(month==3)mon="MAR";
                    if(month==4)mon="APR";
                    if(month==5)mon="MAY";
                    if(month==6)mon="JUN";
                    if(month==7)mon="JUL";
                    if(month==8)mon="AUG";
                    if(month==9)mon="SEP";
                    if(month==10)mon="OCT";
                    if(month==11)mon="NOV";
                    if(month==12)mon="DEC";

                    if(day==1||day==2||day==3||day==4||day==5||day==6||day==7||day==8||day==9)
                        tday="0"+day;
                    else
                        tday=String.valueOf(day);


                    // Set the Selected Date in Select date Button
                    selectDate.setText(tday+"-"+mon+"-"+year);
                    getDay = (String) selectDate.getText();
                    getDay = getDay+'$';
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // create a new DatePickerDialog with values you want to show
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            // create a new TimePickerDialog with values you want to show

        }
        return null;
    }


}
