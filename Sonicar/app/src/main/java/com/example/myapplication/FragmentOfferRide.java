package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChooseRide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOfferRide extends Fragment {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView tv_date;
    private SeekBar seekBar;
    private TextView tv_seekBar;
    private Spinner spinner_dep;
    private Spinner spinner_dest;
    private Button btn;

    List<String> cities=new ArrayList<>();

    public static final String OFFER_RIDE="offerRide";

    //ArrayList<Ride> rideList=new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentOfferRide() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_home.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentChooseRide newInstance(String param1, String param2) {
        FragmentChooseRide fragment = new FragmentChooseRide();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_ride, container, false);


        tv_date = view.findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
                Date d = Calendar.getInstance().getTime();
                datePickerDialog.getDatePicker().setMinDate(d.getTime());
                datePickerDialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int newMonth=month+1;
                String date = year + "/" + newMonth+ "/" + dayOfMonth;
                tv_date.setText(date);
            }
        };

        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setMax(8);
        seekBar.setProgress(1);
        tv_seekBar = view.findViewById(R.id.tv_seekbar);
        tv_seekBar.setText("Number of passagers: " + seekBar.getProgress() + " / " + seekBar.getMax());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                tv_seekBar.setText("Number of passagers: " + progress + " / " + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar.setText("Number of passagers: " + progress_value + " / " + seekBar.getMax());
            }
        });

        spinner_dep = view.findViewById(R.id.spinner_departure);
        spinner_dest = view.findViewById(R.id.spinner_destination);

        ExtractCitiesJSON extractCitiesJSON=new ExtractCitiesJSON(){
            @Override
            protected void onPostExecute(String s) {
                cities.addAll(ExtractCitiesJSON.cities);

                ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, cities);
                spinner_dep.setAdapter(adaptor);
                spinner_dest.setAdapter(adaptor);
            }
        };

        try {
            extractCitiesJSON.execute(new URL("https://pastebin.com/raw/dkVtw1JH"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        btn=view.findViewById(R.id.btn_create_ride);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tv_date.getText().toString().toUpperCase().equals(getResources().getString(R.string.tv_date)))
                    Toast.makeText(getContext().getApplicationContext(),R.string.txt_choose_date,Toast.LENGTH_LONG).show();
                else if(seekBar.getProgress()==0)
                    Toast.makeText(getContext().getApplicationContext(),R.string.txt_choose_nrPass,Toast.LENGTH_LONG).show();
                else
                {
                    String date=tv_date.getText().toString();
                    int nrPassagers=seekBar.getProgress();
                    String city_dep=spinner_dep.getSelectedItem().toString();
                    String city_dest=spinner_dest.getSelectedItem().toString();
                    final Ride ride=new Ride(city_dep,city_dest,date,nrPassagers);

                    SharedPreferences preferences=getContext().getSharedPreferences("shPrefData",0);
                    int user_id=preferences.getInt("user_id",0);
                    ride.setDriver_id(user_id);

                    //add ride in Room
                    final SoniCarDB db= SoniCarDB.getInstance(getContext().getApplicationContext());
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.getRidesDAO().insert(ride);

                        }
                    });

                    Intent intent= new Intent(getContext().getApplicationContext(), RidesOfferedActivity.class);
                    startActivityForResult(intent,200);
                }

            }


        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {

        }
    }
}