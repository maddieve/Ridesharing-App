package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeTravelersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_travelers);

        Ride ride = (Ride) getIntent().getSerializableExtra("rideSelected");
        final int ride_id = ride.getId();

        final SoniCarDB db = SoniCarDB.getInstance(getApplicationContext());



                final List<RideReserved> rr = db.getRidesReservedDAO().getRidesReserved2(ride_id);

                final List<User> users = new ArrayList<>();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference().
                        child("dam-project-users").child("dam-project-users");
                myRef.keepSynced(true);

                final ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {

                                int id = Integer.parseInt(ds.child("id").getValue().toString());

                                for (RideReserved rRes : rr) {

                                    int user_id = rRes.getUser_id();
                                    if (id == user_id) {
                                        users.add(ds.getValue(User.class));
                                    }
                                }

                            }
                        }

                        ListView listView = findViewById(R.id.listView);
                        if (users!=null) {

                            TravelersAdapter adapter = new TravelersAdapter(getApplicationContext(), R.layout.lv_travelers, (ArrayList<User>) users, getLayoutInflater());
                            listView.setAdapter(adapter);
                        } else {

                            Toast.makeText(getApplicationContext(),"You don't have passagers!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                myRef.addValueEventListener(listener);


            }





}