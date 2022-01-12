package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRidesOffered#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRidesOffered extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRidesOffered() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRidesOffered.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRidesOffered newInstance(String param1, String param2) {
        FragmentRidesOffered fragment = new FragmentRidesOffered();
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


    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_rides_offered, container, false);

        SoniCarDB db=SoniCarDB.getInstance(getContext().getApplicationContext());

        SharedPreferences shPrefUserLog=getContext().getSharedPreferences("shPrefData",0);
        final int user_id=shPrefUserLog.getInt("user_id",0);


        ArrayList<Ride> ridesOffered= (ArrayList<Ride>) db.getRidesDAO().getDriverRides(user_id);//display rides
        //Toast.makeText(getContext().getApplicationContext(),ridesOffered.toString(),Toast.LENGTH_LONG).show();

        listView=view.findViewById(R.id.listView);


        if (ridesOffered != null) {
            RideAdapterOf adapter = new RideAdapterOf(getContext().getApplicationContext(), R.layout.lv_driver_rides, ridesOffered, getLayoutInflater());
            listView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(getContext().getApplicationContext(),"Nu ati oferit nicio cursa!", Toast.LENGTH_SHORT).show();
        }







        return view;
    }
}