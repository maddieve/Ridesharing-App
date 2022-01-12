package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RidesSearchedActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Ride> rideList = new ArrayList<>();
    ArrayList<Ride> ridesDB= new ArrayList<>();


    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_searched);

        final Ride ride= (Ride) getIntent().getSerializableExtra(FragmentChooseRide.GENERATE_RIDE);


        final SoniCarDB db=SoniCarDB.getInstance(getApplicationContext());


                ridesDB= (ArrayList<Ride>) db.getRidesDAO().getAll();
                for(Ride rideDB: ridesDB) {

                    if (ride.getDate().toString().equals(rideDB.getDate().toString()) &&
                            (ride.getNr_passagers() < rideDB.getNr_passagers() || ride.getNr_passagers() == rideDB.getNr_passagers()) &&
                            ride.getDestination().equals(rideDB.getDestination()) &&
                            ride.getDeparture().equals(rideDB.getDeparture())) {

                        rideList.add(rideDB);
                    }
                }

                listView=findViewById(R.id.lv);

                if(rideList!=null) {
                    RideAdapterS adapter = new RideAdapterS(getApplicationContext(), R.layout.lv_rides_searched, rideList, getLayoutInflater());
                    listView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Nu exista curse pentru datele introduse!",Toast.LENGTH_SHORT).show();
                }


            }




}