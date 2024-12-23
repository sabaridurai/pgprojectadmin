package com.example.transportadmin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {
private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
bottomNavigationView=findViewById(R.id.nav);
       /* ActionBar actionBar= getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00C697"));
        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);*/
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}



        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.viewarea, new fragment_home());
        fragmentTransaction1.commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_home: {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.viewarea, new fragment_home());
                    fragmentTransaction.commit();
                    return true;

                }
                case R.id.nav_add: {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.viewarea, new fragment_addpersion());
                    fragmentTransaction.commit();
                    return true;

                }
                case R.id.nav_view: {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.viewarea, new fragment_view());
                    fragmentTransaction.commit();
                    return true;

                }

            }
            return false;
        });


    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.nav_home != seletedItemId) setHomeItem(home.this);
        else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}