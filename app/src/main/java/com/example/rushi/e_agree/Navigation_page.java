package com.example.rushi.e_agree;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class Navigation_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String email;
    int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Receives the mail from Login or register activity
        Intent intent=getIntent();
        email=intent.getStringExtra("Email1");
     //   Toast.makeText(getApplicationContext(),email, LENGTH_SHORT).show();

    }
    public void buy(View view)
    {
        startActivity(new Intent(Navigation_page.this, Buy_page.class));
    }

    //to upload the add
    public void sell(View view)
    {
        //to send the mail from navigtaion Activity to sell activity
        Intent i = new Intent(Navigation_page.this, Sell_page.class);
        i.putExtra("Email1",email);
        startActivity(i);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backButtonCount >= 1) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                backButtonCount = 0;
            } else {
                Toast.makeText(this, "Press the back button once again to close the application.", LENGTH_SHORT).show();
                backButtonCount++;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {

            startActivity(new Intent(Navigation_page.this,About_page.class));
        } else if(id == R.id.nav_profile)
        {
           // Toast.makeText(getApplicationContext(),email, LENGTH_SHORT).show();
            //to send the mail from navigtaion Activity to profile activity
            Intent i = new Intent(Navigation_page.this, Profile_page.class);
            i.putExtra("Email1",email);
            startActivity(i);

            //startActivity(new Intent(Navigation_page.this,Profile_page.class));
        }
        else if (id == R.id.nav_buy) {
            startActivity(new Intent(getApplicationContext(),Buy_page.class));

        } else if (id == R.id.nav_sell) {

            //to send the mail from navigtaion Activity to profile activity
            Intent i = new Intent(Navigation_page.this, Sell_page.class);
            i.putExtra("Email1",email);
            startActivity(i);


        }  if (id == R.id.nav_logout) {

            startActivity(new Intent(Navigation_page.this,Login_page.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
