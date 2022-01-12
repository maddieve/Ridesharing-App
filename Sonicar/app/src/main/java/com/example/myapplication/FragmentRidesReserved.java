package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRidesReserved#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRidesReserved extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRidesReserved() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRidesReserved.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRidesReserved newInstance(String param1, String param2) {
        FragmentRidesReserved fragment = new FragmentRidesReserved();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ArrayList<Ride> ridesReserved=new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_rides_reserved, container, false);

        final SoniCarDB db=SoniCarDB.getInstance(getContext().getApplicationContext());

        SharedPreferences shPrefUserLog=getContext().getSharedPreferences("shPrefData",0);
        final int user_id=shPrefUserLog.getInt("user_id",0);


                ArrayList<RideReserved> rr= (ArrayList<RideReserved>) db.getRidesReservedDAO().getRidesReserved(user_id);

                for(RideReserved rideReserved:rr) {
                    int ride_id = rideReserved.getRide_id();
                    Ride ride = db.getRidesDAO().get(ride_id);
                    ridesReserved.add(ride);
                }


        listView=view.findViewById(R.id.listView);

        RideAdapterRes adapter = new RideAdapterRes(getContext().getApplicationContext(), R.layout.lv_rides_reserved, ridesReserved, getLayoutInflater());
        listView.setAdapter(adapter);


        return view;
    }
}