package com.arifulislamanik.attendancepro;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class login extends Activity {

    private static EditText user;
    private static EditText pass;
    private static Button btn;
    private ProgressDialog pDialog;
    public static String t_id;
    int counter = 5;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://attendancepro.host22.com/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.editText_user);
        pass = (EditText) findViewById(R.id.editText_pass);
        btn = (Button) findViewById(R.id.button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AttemptLogin().execute();

            }
        });

    }

    class AttemptLogin extends AsyncTask<String, String, String> {
        /** * Before starting background thread Show Progress Dialog * */
        boolean failure = false;
        @Override

        protected void onPreExecute()
        { super.onPreExecute();
            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args)
        { // TODO Auto-generated method stub // here Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try { List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest( LOGIN_URL, "POST", params);
                // checking log for json response
                 Log.d("Login attempt", json.toString());
                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) { Log.d("Successfully Login!", json.toString());
                    t_id=username;
                    Intent ii = new Intent(login.this,home.class);
                    //finish(); // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity
                    startActivity(ii);
                    return json.getString(TAG_MESSAGE);
                }
                else{
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e)
            { e.printStackTrace();
            }
            return null; } /** * Once the background process is done we need to Dismiss the progress dialog asap * **/
        protected void onPostExecute(String message)
        { pDialog.dismiss();
            if (message != null)
            { Toast.makeText(login.this, message, Toast.LENGTH_LONG).show(); }
        }
    }


}
