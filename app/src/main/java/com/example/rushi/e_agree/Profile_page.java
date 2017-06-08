package com.example.rushi.e_agree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile_page extends AppCompatActivity {

     String email;
    TextView Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        //Receives the mail from Login or register activity
        Intent intent=getIntent();
        email=intent.getStringExtra("Email1");
        TextView Email=(TextView)findViewById(R.id.email);
        Email.setText(email);
    }
}
