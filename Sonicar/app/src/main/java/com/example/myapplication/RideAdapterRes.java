package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;


public class RideAdapterRes extends ArrayAdapter<Ride> {
    private Context context;
    private int resource;
    private ArrayList<Ride> ridesList;
    private LayoutInflater layoutInflater;

    public RideAdapterRes(@NonNull Context context, int resource, ArrayList<Ride> ridesList, LayoutInflater layoutInflater) {
        super(context, resource,ridesList);
        this.context = context;
        this.resource = resource;
        this.ridesList = ridesList;
        this.layoutInflater = layoutInflater;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = layoutInflater.inflate(resource, parent, false);
        final Ride ride = ridesList.get(position);
        if (ride != null) {

            final SoniCarDB db=SoniCarDB.getInstance(getContext().getApplicationContext());

            TextView tv_dest = view.findViewById(R.id.tv_dest);
            tv_dest.setText("Destination: "+ride.getDestination());

            TextView tv_dep = view.findViewById(R.id.tv_dep);
            tv_dep.setText("Departure: "+ride.getDeparture());

            TextView tv_data = view.findViewById(R.id.tv_data);
            tv_data.setText("Date: "+ride.getDate());

            TextView tv_driver_name=view.findViewById(R.id.tv_driver_name);
            int userId=db.getRidesDAO().getDriver_id(ride.getId());
            User user=db.getUsersDAO().get(userId);

            tv_driver_name.setText("Driver: "+user.getLastname()+" "+user.getFirstname());

            TextView tv_driver_phone=view.findViewById(R.id.tv_driver_phone);
            tv_driver_phone.setText("Phone driver: "+user.getPhone());

            final int ride_id=ride.getId();
            SharedPreferences prf=getContext().getSharedPreferences("shPrefData",0);
            final int user_id=prf.getInt("user_id",0);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    RideReserved rideRes=db.getRidesReservedDAO().getRideReserved(ride_id,user_id);
                    int nr_pass_reserved=rideRes.getNr_passagers_reserved();

                    TextView tv_nr_passagers_reserved=view.findViewById(R.id.tv_nr_passagers_reserved);
                    tv_nr_passagers_reserved.setText("Your number reserved seats: "+String.valueOf(nr_pass_reserved));


                    Button btn=view.findViewById(R.id.btn_cancel_ride);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SoniCarDB db=SoniCarDB.getInstance(getContext().getApplicationContext());
                            int nr_pass_res_by_user=db.getRidesReservedDAO().getRideReserved(ride_id,user_id).getNr_passagers_reserved();
                            db.getRidesReservedDAO().deleteWhere(ride_id,user_id);

                            //adauga inapoi nr de pasageri la cursa respectiva
                            int nrPass=db.getRidesDAO().get(ride_id).getNr_passagers();//nr de pasageri de la cursa
                            db.getRidesDAO().increaseNr_passagers(nr_pass_res_by_user,ride_id);

                            Toast.makeText(getContext().getApplicationContext(),"Ati anulat o cursa!",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });


        }


        return view;
    }
}


