package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private Button btn_sign_in;
    private Button btn_log_in;
    private static int REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences shPref=getSharedPreferences("shPrefData",0);
        SharedPreferences.Editor myEdit=shPref.edit();
        myEdit.clear();
        myEdit.apply();


        SharedPreferences shPref2=getSharedPreferences("shPrefUser",0);
        SharedPreferences.Editor myEdit2=shPref2.edit();
        myEdit2.clear();
        myEdit2.apply();

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity1.class);
                startActivity(intent);
            }
        });

        btn_log_in = findViewById(R.id.btn_log_in);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);

                SoniCarDB db=SoniCarDB.getInstance(getApplicationContext());


                startActivity(intent);
            }
        });


    }


}
