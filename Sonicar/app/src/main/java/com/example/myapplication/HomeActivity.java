package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    Intent intent;
    Switch toggle;
    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intent = getIntent();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentIconDashboard()).commit();

        toggle = findViewById(R.id.switch1);
        toggle.setVisibility(View.GONE);

        toggleButton=findViewById(R.id.toggleButton2);
        toggleButton.setVisibility(View.GONE);

    }

        private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_dashboard:
                    {
                        selectedFragment = new FragmentIconDashboard();
                        if(toggle.getVisibility()==View.VISIBLE)
                            toggle.setVisibility(View.GONE);

                        if(toggleButton.getVisibility()==View.VISIBLE)
                            toggleButton.setVisibility(View.GONE);
                    }
                        break;
                    case R.id.nav_rides:
                    {
                        if(toggleButton.getVisibility()==View.VISIBLE)
                            toggleButton.setVisibility(View.GONE);

                        if(toggle.getVisibility()==View.GONE)
                            toggle.setVisibility(View.VISIBLE);
                        if(!toggle.isChecked())
                        {
                            toggle.setText(R.string.btn_choose_ride);
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentChooseRide()).commit();
                        }
                        else if(toggle.isChecked())
                        {
                            toggle.setText(R.string.btn_offer_ride);
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentOfferRide()).commit();
                        }
                        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    toggle.setText(R.string.btn_offer_ride);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentOfferRide()).commit();
                                } else {
                                    toggle.setText(R.string.btn_choose_ride);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentChooseRide()).commit();
                                }
                            }
                        });

                        if (intent.getExtras() != null && intent.getStringExtra(RidesOfferedActivity.SWITCH_OFF).equals("switchOff")) {

                            toggle.setChecked(true);
                            toggle.setText(R.string.btn_offer_ride);
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentOfferRide()).commit();
                        }
                    }
                        break;
                    case R.id.nav_history: {

                        if(toggle.getVisibility()==View.VISIBLE)
                            toggle.setVisibility(View.GONE);

                        if(toggleButton.getVisibility()== View.GONE)
                        {
                            toggleButton.setVisibility(View.VISIBLE);
                            toggleButton.setChecked(true);
                            toggleButton.setText("See your offered rides");
                        }

                        if(!toggleButton.isChecked())
                        {
                            toggleButton.setTextOff("See your reserved rides");
                            toggleButton.setTextColor(getResources().getColor(R.color.colorLightBackground));
                            toggleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentRidesOffered()).commit();
                        }
                        else if(toggleButton.isChecked())
                        {
                            toggleButton.setTextOn("See your offered rides");
                            toggleButton.setTextColor(getResources().getColor(R.color.colorLightBackground));
                            toggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentRidesReserved()).commit();
                        }

                        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    toggleButton.setTextOn("See your offered rides");
                                    toggleButton.setTextColor(getResources().getColor(R.color.colorLightBackground));
                                    toggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentRidesReserved()).commit();
                                } else {
                                     toggleButton.setTextOff("See your reserved rides");
                                    toggleButton.setTextColor(getResources().getColor(R.color.colorLightBackground));
                                    toggleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentRidesOffered()).commit();
                                }
                            }
                        });
                    }
                        break;
                    case R.id.nav_profile: {
                        selectedFragment = new FragmentIconProfile();
                        if(toggle.getVisibility()==View.VISIBLE)
                            toggle.setVisibility(View.GONE);

                        if(toggleButton.getVisibility()==View.VISIBLE)
                            toggleButton.setVisibility(View.GONE);

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameId, new FragmentIconProfile()).commit();

                    }
                        break;
                }

                if(selectedFragment!=null)
                getSupportFragmentManager().beginTransaction().replace(R.id.frameId, selectedFragment).commit();
                return true;
            }
        };

    }



