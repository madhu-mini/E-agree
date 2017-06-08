package com.example.rushi.e_agree;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.EditTextPreference;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.BufferUnderflowException;

import static android.widget.Toast.LENGTH_SHORT;

public class Login_page extends AppCompatActivity
{



    //To set sign up and forget password activity
    TextView textView1;
    TextView textView2;

    //to use for the signup activity
    EditText email,password;
    String  Email,Password;

    String JSON_String;
    Button button1;
    int  backButtonCount=0;
    //On create Activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_page);

            textView1 = (TextView) findViewById(R.id.forgotpassword);
            textView2 = (TextView) findViewById(R.id.signup);
            button1 = (Button) findViewById(R.id.login);

            email = (EditText) findViewById(R.id.login_email);
            password = (EditText) findViewById(R.id.login_password);

            ConnectivityManager connectivityManager;//To check the internet connection
            try
            {
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            catch (Exception e)
            {
                Log.e("Login", "error in connectivitymaneger");
                return;
            }
            final NetworkInfo networkInfo;
            try
            {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            catch (Exception e)
            {
                Log.e("Login page", "Error in network info");
                return;
            }

            //Login activity to Forgot password activity(finalized)
             textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                backButtonCount = 0;
                ConnectivityManager connectivityManager;//to check internet coonection
                try
                {
                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                }
                catch (Exception e)
                {
                    Log.e("Login", "error in connectivitymaneger");
                    return;
                }
                final NetworkInfo networkInfo;
                try
                {
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                }
                catch (Exception e)
                {
                    Log.e("Login page", "Error in network info");
                    return;
                }
                if (networkInfo != null && networkInfo.isConnected())//if internet is on it wiil go to forgot password activity
                    startActivity(new Intent(Login_page.this, Forgotpassword_Page.class));
                else//remains on the same activity
                    Toast.makeText(getApplicationContext(), "No internet connection.",
                            LENGTH_SHORT).show();
                }
            });

        ///Login Acivity to Registeration activity
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View v) {
                    backButtonCount = 0;
                    ConnectivityManager connectivityManager;//to check internet conection
                    try
                    {
                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    }
                    catch (Exception e)
                    {
                    Log.e("Login", "error in connectivitymaneger");
                    return;
                    }
                    final NetworkInfo networkInfo;
                    try
                    {
                        networkInfo = connectivityManager.getActiveNetworkInfo();
                    }
                    catch (Exception e)
                    {
                        Log.e("Login page", "Error in network info");
                    return;
                    }
                    if (networkInfo != null && networkInfo.isConnected()) //if internet is on then it will goto registeration activity
                        startActivity(new Intent(Login_page.this, Register_page.class));
                    else//remains on same activity
                        Toast.makeText(getApplicationContext(), "No internet connection.",
                            LENGTH_SHORT).show();
                }
            });

    }
    //on click activity of login button
    public void login(View view)
    {
       //obtaining the data of text field
        Email=email.getText().toString();
        Password=password.getText().toString();
        //to check field is filled or not
        if (Password.isEmpty() || Email.isEmpty())
        {
                Toast.makeText(getApplicationContext(), "Every Field must be filled compulsory",
                        LENGTH_SHORT).show();
        }
        else
        {
            //check internet connection after filling mail and password and button pressed
            ConnectivityManager connectivityManager;
            try
            {
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            catch (Exception e)
            {
                Log.e("Login","error in connectivitymaneger");
                return;
            }
            final NetworkInfo networkInfo;
            try
            {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            catch (Exception e)
            {
                Log.e("Login page","Error in network info");
                return;
            }
            if(networkInfo!=null && networkInfo.isConnected()) {
                Login_page.BackgroundTask backgroundTask = new Login_page.BackgroundTask();
                backgroundTask.execute(Email, Password);
            }
            else
                Toast.makeText(getApplicationContext(), "No internet connection.",
                        Toast.LENGTH_SHORT).show();

            //finish();


            /*//if network is present and satisfy all condition go to navigation activity
            if(networkInfo!=null && networkInfo.isConnected())
            {//To check registeration recquirement
                startActivity(new Intent(Login_page.this,Navigation_page.class));
            }
            else
                Toast.makeText(getApplicationContext(), "No internet connection.",
                        Toast.LENGTH_SHORT).show();
            */
        }
    }
    //It is to adjust the background activity
    @Override
    public void onBackPressed()// to adjust the back activity
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backButtonCount=0;
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    //Background activity

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String login_info_url;
        @Override
        protected void onPreExecute()
        {
            login_info_url="http://eduolx.890m.com/Eagree_login.php";
        }

        @Override
        protected String doInBackground(String... args)
        {
            String email,password;
            email=args[0];
            password=args[1];
            try
            {
                URL url=new URL(login_info_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data_string= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line="";
                String response=bufferedReader.readLine();
                 Log.d("Respose la alla","response="+response);
                /*while((line= bufferedReader.readLine())!=null)
                {
                    response+=line;
                }*/

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result)
        {
            if(!result.trim().equals("Invalid username or Password")) {
               // Toast.makeText(getApplicationContext(),result, LENGTH_SHORT).show();

                //to send the mail to navigtaion Activity
                Intent i = new Intent(Login_page.this, Navigation_page.class);
                EditText email = (EditText) findViewById(R.id.login_email);
                String Email=email.getText().toString();
                i.putExtra("Email1",Email);
                startActivity(i);
                //startActivity(new Intent(Login_page.this, Navigation_page.class));
            }
            else
                Toast.makeText(getApplicationContext(), "Invalid email or password ", LENGTH_SHORT).show();

        }
    }
}
