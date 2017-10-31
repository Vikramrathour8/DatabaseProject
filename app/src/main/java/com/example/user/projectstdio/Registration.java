package com.example.user.projectstdio;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class Registration extends AppCompatActivity {
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Intent intent=getIntent();
            user_name=intent.getStringExtra(Intent.EXTRA_TEXT);

        Bundle bundle=new Bundle();
        bundle.putString("user_name",user_name);
        Toast.makeText(Registration.this,user_name,Toast.LENGTH_LONG).show();
        Fragment first=new StudentDetails();
        first.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,first).commit();


    }
}
