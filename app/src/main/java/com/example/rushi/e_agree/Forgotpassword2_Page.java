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

public class Forgotpassword2_Page extends AppCompatActivity {

    String email;
    EditText password,cpassword;
    String  Password,Cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword2__page);

        //String email;
        //Receives the mail from forgot password activity
        Intent intent=getIntent();
        email=intent.getStringExtra("Email1");
        //Toast.makeText(getApplicationContext(),email, LENGTH_SHORT).show();
        password= (EditText) findViewById(R.id.new_password);
        cpassword= (EditText) findViewById(R.id.new_password2);

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
    public void save(View view)
    {
        //obtaining the data of text field
        Password=password.getText().toString();
        Cpassword=cpassword.getText().toString();
        //to check field is filled or not
        if (Password.isEmpty() || Cpassword.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Every Field must be filled compulsory",
                    LENGTH_SHORT).show();
        }
        else if (Password.equals(Cpassword))
        {
            Forgotpassword2_Page.BackgroundTask backgroundTask=new Forgotpassword2_Page.BackgroundTask();
            backgroundTask.execute(email,Password);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter both password same",
                    LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String forgotPassword_info_url;
        @Override
        protected void onPreExecute()
        {
            forgotPassword_info_url="http://eduolx.890m.com/Eagree_forgotpassword2.php";
        }

        @Override
        protected String doInBackground(String... args)
        {
            String email,password;
            email=args[0];
            password=args[1];
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

            if(result.trim().equals("Registered Successfully..."))            {

                startActivity(new Intent(Forgotpassword2_Page.this,Login_page.class));
            }
            else
                Toast.makeText(getApplicationContext(), "Error Occures", LENGTH_SHORT).show();

        }
    }
}
