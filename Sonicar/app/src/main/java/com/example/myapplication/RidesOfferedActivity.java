package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RidesOfferedActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ListView listView;
    public static final String SWITCH_OFF = "switchOff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_offered);

        listView = findViewById(R.id.listView);

        //rides list for actual user
        final SoniCarDB db = SoniCarDB.getInstance(getApplicationContext());

        SharedPreferences shPrefUserLog=getSharedPreferences("shPrefData",0);
        final int user_id=shPrefUserLog.getInt("user_id",0);


                ArrayList<Ride> rideList= (ArrayList<Ride>) db.getRidesDAO().getDriverRides(user_id);

                if (rideList != null) {
                    RideAdapter adapter = new RideAdapter(getApplicationContext(), R.layout.lv_rides_offered, rideList, getLayoutInflater());
                    listView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Nu ati oferit nicio cursa!", Toast.LENGTH_SHORT).show();
                }


        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_OK, getIntent());
                finish();
            }
        });


    }

}

