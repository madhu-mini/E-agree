package com.example.rushi.e_agree;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Register_page extends Activity
{

    EditText firstname,lastname,email,password,cpassword,mobileno;
    String   Firstname,Lastname,Email,Password,Cpassword,Mobileno;
    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        //Initialization of variables
        firstname = (EditText) findViewById(R.id.user_firstname);
        lastname = (EditText) findViewById(R.id.user_lastname);
        email = (EditText) findViewById(R.id.user_email);
        password = (EditText) findViewById(R.id.user_password);
        cpassword = (EditText) findViewById(R.id.user_cpassword);
        mobileno = (EditText) findViewById(R.id.user_mobileno);
        button1 = (Button) findViewById(R.id.register);
    }
    public void registeration(View view)//on click activity of register button
    {
        Firstname = firstname.getText().toString();
        Lastname = lastname.getText().toString();
        Email = email.getText().toString();
        Password = password.getText().toString();
        Cpassword = cpassword.getText().toString();
        Mobileno = mobileno.getText().toString();

        if (Firstname.isEmpty() || Lastname.isEmpty() || Email.isEmpty() || Password.isEmpty() || Cpassword.isEmpty() || Mobileno.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Every field must be filled up",
                    Toast.LENGTH_SHORT).show();
        }
        else//if all filled is fill up
        {
            if (Password.equals(Cpassword))//to check the password are equal or not
            {//Register activity
                BackgroundTask backgroundTask=new BackgroundTask();
                backgroundTask.execute(Firstname,Lastname,Email,Password,Mobileno);
                finish();

                ConnectivityManager connectivityManager;//check internet connection
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
                if(networkInfo!=null && networkInfo.isConnected())//if network is present and satisfy all condition go to navigation activity
                {//To check registeration recquirement

                    //to send the mail to navigtaion Activity
                    Intent i = new Intent(Register_page.this, Navigation_page.class);
                    EditText email = (EditText) findViewById(R.id.user_email);
                    String Email=email.getText().toString();
                    i.putExtra("Email1",Email);
                    startActivity(i);

                    //startActivity(new Intent(Register_page.this,Navigation_page.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "No internet connection.",
                            Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(getApplicationContext(), "Password and confirm password must bee the same",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String add_info_url;

        @Override
        protected void onPreExecute()
        {
                add_info_url="http://eduolx.890m.com/Eagree_register.php";
        }

        @Override
        protected String doInBackground(String... args)
        {
            String fname,lname,email,password,mobile;
            fname=args[0];
            lname=args[1];
            email=args[2];
            password=args[3];
            mobile=args[4];

            try
            {
                URL url=new URL(add_info_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data_string= URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")+"&"+
                                    URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(lname,"UTF-8")+"&"+
                                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                                    URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                                    URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return " Registered Sucessfully ";
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
            Toast.makeText(getApplicationContext(),result,
                    Toast.LENGTH_SHORT).show();

        }
    }
}
