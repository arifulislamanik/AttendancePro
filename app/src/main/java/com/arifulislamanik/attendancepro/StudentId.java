package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class StudentId extends Activity {

    String name;
    String courseId;
    InputStream is=null;
    String attendanceStatus=null;
    String result=null;
    String line=null;
    int code;
    private ProgressDialog pDialog;
    ListView lv;
    SearchView s;
    Spinner sp;
    int checkVal=0;
    SearchableAdapter la;
    Button check,smsAll;
    String course_name[] = new String[100];
    String studentId[] = new String[100];
    String studentIdf[] = new String[100];
    String studentIdc[] = new String[100];
    String attendanceStat[] = new String[100];
    String phnArray[] =  new String[100];
    List<String> studentData =new ArrayList<String>();
    int a=0;
    int p=0;
    int l=0;
    int k1=0;
    double sts[] = new double[100];
    double stsf[] = new double[100];
    double stsc[] = new double[100];
    double total=0;
    EditText ed;
    public static String s_id;
    // String course[] = {"asdf","asdfasdf"};
    int k=0,m=0,n=0,phn=0;
    int j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_id);
        courseId = CourseReport.c_id;

        lv=(ListView) findViewById(R.id.idList);
        sp = (Spinner) findViewById(R.id.spinnerP);
        check = (Button) findViewById(R.id.summary);
        check.setVisibility(View.INVISIBLE);
        smsAll = (Button) findViewById(R.id.allSms);
        smsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentId.this);
                builder.setMessage("Are you sure to send SMS to all students at once??");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Select_Phn().execute();
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

        List<String> list = new ArrayList<String>();
        list.add("Show All");
        list.add("Below 90");
        list.add("Below 85");
        list.add("Below 80");
        list.add("Below 75");
        list.add("Below 70");
        list.add("Below 60");
        list.add("Below 50");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //sp.setAdapter(dataAdapter);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);


        sp.setAdapter(dataAdapter);






        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //c_id =(String) lv.getItemAtPosition(position);
                TextView textView = (TextView) view.findViewById(R.id.s_id);
                s_id =textView.getText().toString();
                System.out.println(s_id);
                Intent intent = new Intent(StudentId.this,att_form.class);
                startActivity(intent);
                // Toast.makeText(takeNote.this, "You Clicked at " +noteName[+ position], Toast.LENGTH_SHORT).show();
                Log.e("pass 8", "connection success ");
            }
        });

        new Select_students().execute();
        //lv.setAdapter(new listAdapter(Take_att.this, course, 2));
        //la = new SearchableAdapter(StudentId.this,studentData);
        //lv.setAdapter(new listAdapter1(Take_att.this, course_name, j));

        ed = (EditText) findViewById(R.id.editText);
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.setHint("");
            }
        });
       ed.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

               int zz=0;
               k=0;
               String str = ed.getText().toString();
               for(zz=0;zz<j;zz++)
               {

                   //CharSequence cs = str.toString();
                   System.out.println(studentId[zz]+" "+str+" "+zz);
                        if(studentId[zz].contains(str))
                        {
                            studentIdf[k]=studentId[zz];
                            stsf[k]=sts[zz];
                            System.out.println(studentId[zz]);
                            System.out.println(studentIdf[k]);
                            k++;
                        }
               }
               lv.setAdapter(new ListAdapterP(StudentId.this,studentIdf,stsf, k));
           }
       });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentId.this,summary_report.class));
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sp.getSelectedItemPosition()==0)
                {
                    checkVal=101;
                }
                if(sp.getSelectedItemPosition()==1)
                {
                    checkVal=90;
                }
                else if(sp.getSelectedItemPosition()==2) {
                    checkVal=85;
                }
                else if(sp.getSelectedItemPosition()==3) {
                    checkVal=80;
                }
                else if(sp.getSelectedItemPosition()==4) {
                    checkVal=75;
                }
                else if(sp.getSelectedItemPosition()==5) {
                    checkVal=70;
                }
                else if(sp.getSelectedItemPosition()==6) {
                    checkVal=60;
                }
                else if(sp.getSelectedItemPosition()==7) {
                    checkVal=50;
                }
                for(int b=0;b<j;b++)
                {
                    System.out.println("gahfu"+sts[b]);
                    System.out.println("gahfu"+checkVal);
                    if(sts[b]<(double)checkVal)
                    {
                        studentIdc[m]=studentId[b];
                        stsc[m]=sts[b];
                        System.out.println("gahfu"+studentIdc[m]);
                        m++;

                    }
                }
                lv.setAdapter(new ListAdapterP(StudentId.this,studentIdc,stsc,m));
                m=0;
                System.out.println("aygfhhafjifhjiagfujhang"+sp.getSelectedItemPosition());
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
            pDialog = new ProgressDialog(StudentId.this);
            pDialog.setMessage("Preparing the Student's List...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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
        {  pDialog.dismiss();
            lv.setAdapter(new ListAdapterP(StudentId.this,studentId,sts, j));
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
//                System.out.println("statusP"+p);
//                System.out.println("statusl"+l);
//                System.out.println("statusa"+a);

                double v1 = (double) ((p + l - lp) * 100);
//                System.out.println("status"+v1);
                double v2 = (double) (attendanceStatus.length() );
//                System.out.println("status"+v2);
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
            { Toast.makeText(StudentId.this, message, Toast.LENGTH_LONG).show(); }
        }


    }

    class Select_Phn extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(StudentId.this);
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
                HttpPost httppost = new HttpPost("http://attendancepro.host22.com/get_phn.php");
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
                String s = "{\"contactNo\":\"";
                result=result.replace(s,"");
                result=result.replace("\",\"","");
                result=result.replace("\"}","^");


                System.out.println(result);
                String str = "";
                for(i=1;i<result.length();i++)
                {
                    // System.out.println(result.length());
                    if(result.charAt(i)=='^')
                    {
                        phnArray[phn]=str;
                        System.out.println("PHNNO"+phnArray[phn]);
                        phn++;
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
     {
// pDialog.dismiss();
//            lv.setAdapter(new ListAdapterP(StudentId.this,studentId,sts, j));
//            for(k1=0;k1<j;k1++) {
//                attendanceStatus =  attendanceStat[k1];
//                System.out.println("status"+attendanceStatus);
//                for (k = 0; k < attendanceStatus.length() ; k++) {
//                    if (attendanceStatus.charAt(k) == 'P')
//                        p++;
//                    else if (attendanceStatus.charAt(k) == 'A')
//                        a++;
//                    else
//                        l++;
//                }
//
//                int lp = l / 3;
////                System.out.println("statusP"+p);
////                System.out.println("statusl"+l);
////                System.out.println("statusa"+a);
//
//                double v1 = (double) ((p + l - lp) * 100);
////                System.out.println("status"+v1);
//                double v2 = (double) (attendanceStatus.length() );
////                System.out.println("status"+v2);
//                //total = ((p+l-lp)*100)/(attendanceStatus.length()-1);
//                // String parcentage = Double.toString()
//                total = v1 / v2;
//                System.out.println("status"+total);
//
//                sts[k1] =  total;
//
//                a=0;
//                l=0;
//                p=0;
//                total =0;
//
//
//            }
         final String s1= "Your Attendance Parcentage in ";
         final String ss1= " Course is = ";

         final String s3 ="%. Maintain this to get full marks.";
         final String s4 ="%. Be Regular and bring the percentage to 95% or more to get full Marks ";
         final String s6 ="%. Your percentage is in danger zone. Please be more regular. ";
         final String s5= "%. Which is totally unsatisfactory. To Sit in the Exam please ensure at least 70%.";

         int q = 0;
         for (q = 0; q < n; q++) {
             try {
                 String smsString1 = "";
                 String s2= String.format("%.2f",sts[q]);
                 if(sts[q]>=95)
                 {
                     smsString1 =s1+courseId+ss1+s2+s3;

                 }
                 else if(sts[q]<95 && sts[q]>=80)
                 {
                     smsString1=s1+courseId+ss1+s2+s4;

                 }
                 else if(sts[q]<80 && sts[q]>=70)
                 {
                     smsString1=s1+courseId+ss1+s2+s6;

                 }
                 else if(sts[q]<70)
                 {
                     smsString1=s1+courseId+ss1+s2+s5;

                 }
                 SmsManager smsManager = SmsManager.getDefault();
                 smsManager.sendTextMessage(phnArray[q], null, smsString1, null, null);
                 Toast.makeText(getApplicationContext(), "SMS Sent!",
                         Toast.LENGTH_LONG).show();
             } catch (Exception e) {
                 Toast.makeText(getApplicationContext(),
                         "SMS failed, please try again later!",
                         Toast.LENGTH_LONG).show();
                 e.printStackTrace();
             }
         }
            if (message != null)
            { Toast.makeText(StudentId.this, message, Toast.LENGTH_LONG).show(); }
        }


    }

}
