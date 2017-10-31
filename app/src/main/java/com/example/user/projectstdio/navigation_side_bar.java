package com.example.user.projectstdio;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class navigation_side_bar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView mTextView;
    private String msg;
    public final static String HOME_URL = "https://satishmhetre18.000webhostapp.com/home.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_side_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent =getIntent();
         msg=null;
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft;
            msg = intent.getStringExtra(Intent.EXTRA_TEXT);
            ft=fragmentManager.beginTransaction();
            Fragment homePage=new HomePage();
            Bundle bundle=new Bundle();
            bundle.putString("id",msg);
            bundle.putString("table","student");
            homePage.setArguments(bundle);
            ft.replace(R.id.frame_container,homePage);
            ft.commit();

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_side_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft;

        if (id == R.id.nav_menu) {

             ft=fragmentManager.beginTransaction();
            Fragment homePage=new HomePage();
            Bundle bundle=new Bundle();
            bundle.putString("id",msg);
            bundle.putString("table","student");
            homePage.setArguments(bundle);
             ft.replace(R.id.frame_container,homePage);
             ft.commit();

        } else if (id == R.id.nav_my_app) {

            MyApplication list=new MyApplication();
            Bundle bundle=new Bundle();
            bundle.putString("std_id",msg);
            list.setArguments(bundle);
            ft=fragmentManager.beginTransaction();
            ft.replace(R.id.frame_container,list);
            ft.commit();
        }
        else if (id == R.id.nav_edit_app) {
            Fragment list=new College_list();
            Bundle bundle=new Bundle();
            bundle.putString("std_id",msg);
            list.setArguments(bundle);
            ft=fragmentManager.beginTransaction();
            ft.replace(R.id.frame_container,list);
            ft.commit();

        } else if (id == R.id.nav_result) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
