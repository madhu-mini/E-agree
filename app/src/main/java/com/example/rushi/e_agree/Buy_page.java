package com.example.rushi.e_agree;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class Buy_page extends AppCompatActivity {

    String Json_String;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        Toast.makeText(getApplicationContext(), "Click on the image to contact the seller.", LENGTH_SHORT).show();
        BackgroundTask b=new BackgroundTask();
        b.execute();

    }
    public void sendEmail(View view)
    {
        //Toast.makeText(getApplicationContext(), "Sending email ", LENGTH_SHORT).show();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I want to buy your product(@infoegree@gmail.com).");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url="http://eduolx.890m.com/Eagree_buy.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                StringBuilder stringBuilder=new StringBuilder();
                while ((Json_String = bufferedReader.readLine())!=null)
                {
                    Log.d("json string",""+Json_String);
                    stringBuilder.append(Json_String+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String res) {
            List<BuyInfo> result = new ArrayList<BuyInfo>();
            if (res==null)
            {
                Log.e("Result null","null string");
            }
            try {
                JSONObject jsonObject = new JSONObject(res);
                String ans;
                int count = 0;

                JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                Log.d("No of notices",""+jsonArray.length());

                String imagePath,name,price,quantity,mobileno,email,address;
                while (count<jsonArray.length())
                {
                    JSONObject jo = jsonArray.getJSONObject(count);

                    imagePath=jo.getString("image");
                    name=jo.getString("name");
                    price=jo.getString("price");
                    quantity=jo.getString("quantity");
                    mobileno=jo.getString("mobileno");
                    email=jo.getString("email");
                    address=jo.getString("address");
                    BuyInfo b= new BuyInfo(imagePath,name,price,quantity,mobileno,email,address);
                    Log.d("buyinfo",""+b);
                    result.add(b);
                    count++;
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            setContentView(R.layout.activity_buy_page);

/*
            ListAdapter lista=new ArrayAdapter<BuyInfo>(this,R.layout.,result);
            ListView listView = (ListView)findViewById(R.id.listview);
            listView.setAdapter(lista);
*/
            try {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                RowAdapter rowAdapter = new RowAdapter(result);
                RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(pLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(rowAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                        //here to write onclick method



                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {



                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
                while (true);
            }
        }

    }

}
