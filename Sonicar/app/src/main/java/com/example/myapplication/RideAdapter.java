package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class RideAdapter extends ArrayAdapter<Ride> {
    private Context context;
    private int resource;
    private ArrayList<Ride> ridesList;
    private LayoutInflater layoutInflater;

    public RideAdapter(@NonNull Context context, int resource, ArrayList<Ride> ridesList, LayoutInflater layoutInflater) {
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
        Ride ride = ridesList.get(position);
        if (ride != null) {
            TextView tv_dest = view.findViewById(R.id.tv_dest);
            String s="Destination: ";
            tv_dest.setText(s+ride.getDestination());

            TextView tv_dep = view.findViewById(R.id.tv_dep);
            tv_dep.setText("Departure: "+ride.getDeparture());

            TextView tv_data = view.findViewById(R.id.tv_data);
            tv_data.setText("Date: "+ride.getDate());

            TextView tv_nrSeatsDispon = view.findViewById(R.id.tv_nrSeatsDispon);
            tv_nrSeatsDispon.setText("Number available seats: "+String.valueOf(ride.getNr_passagers()));
        }

        return view;
    }
}


