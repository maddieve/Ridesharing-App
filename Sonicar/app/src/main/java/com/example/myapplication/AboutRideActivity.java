package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AboutRideActivity extends AppCompatActivity {
    private Intent intent;
    private Button btn;
    private SoniCarDB db;
    private Ride ride;
    private Ride rideDB;
    private User user;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_email;
    private TextView tv_dest;
    private TextView tv_dep;
    private TextView tv_data;
    private TextView tv_nrSeatsDispon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ride);

        intent=getIntent();
        ride= (Ride) intent.getSerializableExtra("ride");

        db=SoniCarDB.getInstance(getApplicationContext());
        rideDB=db.getRidesDAO().get(ride.getId());

        //get and display info about rideDB
        tv_dest=findViewById(R.id.tv_dest);
        tv_dest.setText("Destination: "+rideDB.getDestination());

        tv_dep=findViewById(R.id.tv_dep);
        tv_dep.setText("Departure: "+rideDB.getDeparture());

        tv_data=findViewById(R.id.tv_data);
        tv_data.setText("Date: "+rideDB.getDate().toString());

        tv_nrSeatsDispon=findViewById(R.id.tv_nrSeatsDispon);
        tv_nrSeatsDispon.setText("Number available seats: "+ String.valueOf(rideDB.getNr_passagers()));

        //

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                int driver_id=db.getRidesDAO().getDriver_id(rideDB.getDriver_id());
                user=db.getUsersDAO().get(driver_id);

                //get and display info about user

                tv_name=findViewById(R.id.tv_name);
                tv_name.setText("Driver's name: "+user.getLastname()+" "+user.getFirstname());

                tv_phone=findViewById(R.id.tv_phone);
                tv_phone.setText("Driver's phone: "+user.getPhone());

                tv_email=findViewById(R.id.tv_email);
                tv_email.setText("Driver's email: "+user.getEmail());

            }
        });



        btn=findViewById(R.id.btn_get_ride);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(AboutRideActivity.this).
                        setTitle("Confirmare rezervare").
                        setMessage("Sigur doriti sa rezervati acesta cursa?")
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(getApplicationContext(), "Nu ati rezervat cursa!", Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();

                            }
                        }).setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences shPrefData=getSharedPreferences("shPrefData",0);
                                final int nr_passagers=shPrefData.getInt("nr passagers",0);
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        db.getRidesDAO().decreaseNr_passagers(nr_passagers, ride.getId());

                                    }
                                });

                        //add in RideReserved

                        SharedPreferences preferences=getSharedPreferences("shPrefData",0);
                        int user_id=preferences.getInt("user_id",0);
                        final RideReserved rideReserved=new RideReserved(user_id,ride.getId(),nr_passagers);

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                db.getRidesReservedDAO().insert(rideReserved);
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Ati rezervat cursa!", Toast.LENGTH_LONG).show();

                            }
                        }).create();

                dialog.show();

            }
        });


    }
}