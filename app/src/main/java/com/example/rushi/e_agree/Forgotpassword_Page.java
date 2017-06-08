package com.example.rushi.e_agree;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import static android.widget.Toast.LENGTH_SHORT;

public class Forgotpassword_Page extends AppCompatActivity {


    EditText email,mobileno;
    String  Email,Mobileno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword__page);

        email = (EditText) findViewById(R.id.forgot_email);
        mobileno= (EditText) findViewById(R.id.forgot_mobileno);

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

    }
    public void next(View view)
    {
        //obtaining the data of text field
        Email=email.getText().toString();
        Mobileno=mobileno.getText().toString();
        //to check field is filled or not
        if (Mobileno.isEmpty() || Email.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Every Field must be filled compulsory",
                    LENGTH_SHORT).show();
        }
        else
        {
            Forgotpassword_Page.BackgroundTask backgroundTask=new Forgotpassword_Page.BackgroundTask();
            backgroundTask.execute(Email,Mobileno);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String forgotPassword_info_url;
        @Override
        protected void onPreExecute()
        {
                forgotPassword_info_url="http://eduolx.890m.com/Eagree_forgotpassword1.php";
        }

        @Override
        protected String doInBackground(String... args)
        {
            String email,mobile;
            email=args[0];
            mobile=args[1];
            try
            {
                URL url=new URL(forgotPassword_info_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data_string= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line="";
                String response=bufferedReader.readLine();
                Log.d("Respose la alla","response="+response);
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

            //trim removes the starting and ending spaces of echo

            if(!result.trim().equals("Invalid username or Password"))            {

                //to send the mail to forgotpassword Activity2
                Intent i = new Intent(Forgotpassword_Page.this, Forgotpassword2_Page.class);
                EditText email = (EditText) findViewById(R.id.forgot_email);
                String Email=email.getText().toString();
                i.putExtra("Email1",Email);
                                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(), "Invalid email or mobile no ", LENGTH_SHORT).show();

        }
    }
}
