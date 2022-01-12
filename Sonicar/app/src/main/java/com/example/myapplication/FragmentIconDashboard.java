package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIconDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIconDashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentIconDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentIconDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIconDashboard newInstance(String param1, String param2) {
        FragmentIconDashboard fragment = new FragmentIconDashboard();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_icon_dashboard, container, false);

        TextView tv=view.findViewById(R.id.textView_seeMore);
        final TextView tvJSON=view.findViewById(R.id.textViewJSON);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopularDestinationsJSON json=new PopularDestinationsJSON(){
                    @Override
                    protected void onPostExecute(String s) {

                        tvJSON.setText(PopularDestinationsJSON.stringJSON);

                    }
                };

                try {
                    json.execute(new URL("https://pastebin.com/raw/tQN5SyVf"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }
}