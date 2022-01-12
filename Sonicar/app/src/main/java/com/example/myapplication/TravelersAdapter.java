package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class TravelersAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;
    private ArrayList<User> list;
    private LayoutInflater layoutInflater;

    public TravelersAdapter(@NonNull Context context, int resource, ArrayList<User> list, LayoutInflater layoutInflater) {
        super(context, resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.layoutInflater = layoutInflater;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        final User user = list.get(position);



        TextView tv_name=view.findViewById(R.id.tv_name);
        tv_name.setText("Name: "+user.getLastname()+" "+user.getFirstname());

        TextView tv_phone=view.findViewById(R.id.tv_phone);
        tv_phone.setText("Phone: "+user.getPhone());

        TextView tv_email=view.findViewById(R.id.tv_email);
        tv_email.setText("Email: "+user.getEmail());

        SharedPreferences pref=getContext().getSharedPreferences("shPrefData",0);
        int ride_id=pref.getInt("ride_id",0);
        int user_id=user.getId();

        SoniCarDB db=SoniCarDB.getInstance(getContext().getApplicationContext());
        RideReserved rideRes=db.getRidesReservedDAO().getRideReserved(ride_id,user_id);
        int nr_pass_reserved=rideRes.getNr_passagers_reserved();

        TextView tv_nr_passagers_reserved=view.findViewById(R.id.tv_nr_passagers_reserved);
        tv_nr_passagers_reserved.setText("Number reserved seats: "+String.valueOf(nr_pass_reserved));

        return view;
    }
}


