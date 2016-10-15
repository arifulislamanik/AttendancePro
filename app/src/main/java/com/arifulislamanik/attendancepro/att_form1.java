package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class att_form1 extends Activity {

    //String name;
    String courseId;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    private ProgressDialog pDialog;
    ListView lv;
    TextView id;
    TextView name,present,absent,late,tc,parcent;
    //String studentName[] = new String[100];
    String studentName;
    String studentId;
    int a=0;
    int p=0;
    int l=0;
    int k=0,n=0;
    public static int q=0;
    double total=0;
    ImageButton b1,b2;
    Button sms,email;
    String attendanceStatus="";
    String smsString="";
    String emailAdd="";
    String emailString="";
    String emailSub="";
    String phnNo="";
    char attendanceStat[] = new char[100];
    String dateArray[] = new String[100];
    public static String disp[] = new String[100];
    public static String s_id;
    // String course[] = {"asdf","asdfasdf"};
    int j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }
        setContentView(R.layout.activity_att_form);
        studentId = StudentId.s_id;
        courseId = CourseReport.c_id;
        Intent i = this.getIntent();
        if(i!=null)
        {
            studentId=i.getExtras().getString("id");
            courseId=Take_att.c_id;
            System.out.println("SWNR"+studentId);
        }

        id = (TextView) findViewById(R.id.idField);
        name = (TextView) findViewById(R.id.nameField);
        present = (TextView) findViewById(R.id.presentField);
        absent = (TextView) findViewById(R.id.absentField);
        late = (TextView) findViewById(R.id.lateField);
        parcent = (TextView) findViewById(R.id.parcentageField);
        tc= (TextView) findViewById(R.id.totalField);

        id.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        present.setVisibility(View.INVISIBLE);
        late.setVisibility(View.INVISIBLE);
        absent.setVisibility(View.INVISIBLE);
        tc.setVisibility(View.INVISIBLE);
        parcent.setVisibility(View.INVISIBLE);
        b1 = (ImageButton) findViewById(R.id.abs_img);
        b2= (ImageButton) findViewById(R.id.late_img);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        sms = (Button) findViewById(R.id.button2);
        sms.setVisibility(View.INVISIBLE);
        email = (Button) findViewById(R.id.button3);
        email.setVisibility(View.INVISIBLE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("OC_" + attendanceStatus);
                for (int i = 0; i < n; i++) {
                    System.out.println("OC_" + attendanceStat[i]);
                    if(attendanceStat[i]=='A')
                    {
                        disp[q]=dateArray[i];
                        System.out.println("OC_" + disp[q]);
                        q++;
                    }
                    System.out.println("OC_" + dateArray[i]);
                }
                FragmentManager fm = getFragmentManager();
                MyDialogFragment dialogFragment = new MyDialogFragment ();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("OC_" + attendanceStatus);
                for (int i = 0; i < n; i++) {
                    System.out.println("OC_" + attendanceStat[i]);
                    if(attendanceStat[i]=='L')
                    {
                        disp[q]=dateArray[i];
                        System.out.println("OC_" + disp[q]);
                        q++;
                    }
                    System.out.println("OC_" + dateArray[i]);
                }
                FragmentManager fm = getFragmentManager();
                MyDialogFragment dialogFragment = new MyDialogFragment ();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(att_form1.this);
                builder.setMessage("Are you sure to send SMS??");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phnNo, null, smsString, null, null);
                                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                                            Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            "SMS failed, please try again later!",
                                            Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
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

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = emailAdd;
//                String subject = emailSub;
//                String message = textMessage.getText().toString();



                System.out.println(emailAdd);
                System.out.println(emailSub);
                System.out.println(emailString);
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, emailSub);
                email.putExtra(Intent.EXTRA_TEXT, emailString);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));



            }
        });

        //lv.setAdapter(new listAdapter1(Take_att.this, course_name, j));

        new Select_students().execute();
        //lv.setAdapter(new listAdapter(Take_att.this, course, 2));

    }



    class Select_students extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(att_form1.this);
            pDialog.setMessage("Preparing the Student's List...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) { // TODO Auto-generated method stub // here Check for success tag
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("studentId", studentId));
            nameValuePairs.add(new BasicNameValuePair("courseId", courseId));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://attendancepro.host22.com/get_attendance1.php");
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
                    sb.append(line);

                }
                result = sb.toString();
                System.out.println(result);
                int i=0;
                String s = "{\"studentName\":\"";
                result=result.replace(s,"");
                result=result.replace("\":\"","");
                result=result.replace("\",\"","");
                result=result.replace("contactNo","&");
                result=result.replace("\"}","");
                result=result.replace("\",\"","");
                result=result.replace("attendanceStatus","^");
                result=result.replace("dateString","#");
                result=result.replace("emailAddress","~");
                System.out.println(result);


                int flag=0;
                String str = "";
                for(i=0;i<result.length();i++)
                {
                    // System.out.println(result.length());
                    if(result.charAt(i)=='&')
                    {
                        studentName = str;
                        //j++;
                        System.out.println("name"+studentName);
                        str="";
                    }
                    else if(result.charAt(i)=='~')
                    {
                        phnNo=str;
                        System.out.println("PhnNO"+phnNo);
                        //n++;
                        str="";
                    }
                    else if(result.charAt(i)=='^')
                    {
                        emailAdd=str;
                        System.out.println("S"+emailAdd);
                        //n++;
                        str="";
                    }
                    else if(result.charAt(i)=='#')
                    {
                        attendanceStatus=str;
                        System.out.println("ASTS"+attendanceStatus);
                        //n++;
                        str="";
                    }

                    else if(result.charAt(i)=='$')
                    {
                        dateArray[n]=str;
                        System.out.println("date"+dateArray[n]);
                        n++;
                        str="";
                    }
                    else
                        str=str+result.charAt(i);
                }
                System.out.println(attendanceStatus);
//                result=result.replace("\n"+attendanceStatus,"");
//                studentName=result;

                //            System.out.println(result);
                // String str = "";
//                for(i=1;i<result.length();i++)
//                {
//                    if(result.charAt(i)=='$')
//                    {
//                        studentName[j] = str;
//                        System.out.println(studentName[j]);
//
//                        j++;
//                        str="";
//                    }
//                    else
//                        str=str+result.charAt(i);
//                }

                is.close();

                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }

            return null;
        }
        protected void onPostExecute(String message)
        { pDialog.dismiss();
            // lv.setAdapter(new listAdapter2(att_form.this,studentId, j));
            id.setText(studentId);
            name.setText(studentName);


            for(k=0;k<attendanceStatus.length();k++)
            {
                attendanceStat[k]=attendanceStatus.charAt(k);
                if(attendanceStatus.charAt(k)=='P')
                    p++;
                else if(attendanceStatus.charAt(k)=='A')
                    a++;
                else
                    l++;
            }

            int lp = l/3;

            double v1 = (double) ((p+l-lp)*100);
            double v2 = (double) (attendanceStatus.length());
            //total = ((p+l-lp)*100)/(attendanceStatus.length()-1);
            // String parcentage = Double.toString()
            total = v1/v2;
            System.out.println(attendanceStatus.length());
            System.out.println(p);
            System.out.println(a);
            System.out.println(l);
            System.out.println(total);
            String s1= "Your Attendance Parcentage in ";
            String ss1= " Course is = ";
            String s2= String.format("%.2f",total);
            String s3 ="%. Maintain this to get full marks.";
            String s4 ="%. Be Regular and bring the percentage to 95% or more to get full Marks ";
            String s6 ="%. Your percentage is in danger zone. Please be more regular. ";
            String s5= "%. Which is totally unsatisfactory. To Sit in the Exam please ensure at least 70%.";
            if(total>=95)
            {
                smsString=s1+courseId+ss1+s2+s3;
                emailString=s1+courseId+ss1+s2+s3;
            }
            else if(total<95 && total>=80)
            {
                smsString=s1+courseId+ss1+s2+s4;
                emailString=s1+courseId+ss1+s2+s4;
            }
            else if(total<80 && total>=70)
            {
                smsString=s1+courseId+ss1+s2+s6;
                emailString=s1+courseId+ss1+s2+s6;
            }
            else if(total<70)
            {
                smsString=s1+courseId+ss1+s2+s5;
                emailString=s1+courseId+ss1+s2+s5;
            }

            emailSub= "Attendance Status of COURSE "+courseId;


            System.out.println(smsString);




            tc.setText(Integer.toString(attendanceStatus.length()));
            present.setText(Integer.toString(p));
            absent.setText(Integer.toString(a));
            late.setText(Integer.toString(l));
            //parcent.setText(Double.toString(total));
            parcent.setText(String.format("%.2f",total)+"%");

            if(total>=85)
                parcent.setTextColor(Color.rgb(16,83,255));
            else if( total<85 && total >=75)
                parcent.setTextColor(Color.rgb(255,140,12));
            else
                parcent.setTextColor(Color.RED);

            tc.setVisibility(View.VISIBLE);
            id.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            present.setVisibility(View.VISIBLE);
            absent.setVisibility(View.VISIBLE);
            late.setVisibility(View.VISIBLE);
            parcent.setVisibility(View.VISIBLE);
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            sms.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            if(absent.getText().toString().equals("0"))
            {
                b1.setVisibility(View.INVISIBLE);

            }
            if(late.getText().toString().equals("0"))
            {
                b2.setVisibility(View.INVISIBLE);

            }
            if (message != null)
            { Toast.makeText(att_form1.this, message, Toast.LENGTH_LONG).show(); }
        }


    }
}
