package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class RideAdapterOf extends ArrayAdapter<Ride> {
    private Context context;
    private int resource;
    private List<Ride> ridesList;
    private LayoutInflater layoutInflater;

    public RideAdapterOf(@NonNull Context context, int resource, ArrayList<Ride> ridesList, LayoutInflater layoutInflater) {
        super(context, resource,ridesList);
        this.context = context;
        this.resource = resource;
        this.ridesList = ridesList;
        this.layoutInflater = layoutInflater;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        final Ride ride = ridesList.get(position);


        if (ride != null) {

            Button btn=view.findViewById(R.id.btn_see_travelers);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext().getApplicationContext(),SeeTravelersActivity.class);

                    SharedPreferences pref=getContext().getSharedPreferences("shPrefData",0);
                    SharedPreferences.Editor myEdit=pref.edit();
                    myEdit.putInt("ride_id",ride.getId());
                    myEdit.apply();

                    intent.putExtra("rideSelected", ride);
                    getContext().startActivity(intent);

                }

            });


            TextView tv_dest = view.findViewById(R.id.tv_dest);
            tv_dest.setText("Destination: "+ride.getDestination());

            TextView tv_dep = view.findViewById(R.id.tv_dep);
            tv_dep.setText("Departure: "+ride.getDeparture());

            TextView tv_data = view.findViewById(R.id.tv_data);
            tv_data.setText("Date: "+ride.getDate().toString());

            TextView tv_nrSeatsDispon = view.findViewById(R.id.tv_nrSeatsDispon);
            tv_nrSeatsDispon.setText("Number available seats: "+String.valueOf(ride.getNr_passagers()));
        }

        return view;
    }
}


