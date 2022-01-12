package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIconProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIconProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentIconProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentIconProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIconProfile newInstance(String param1, String param2) {
        FragmentIconProfile fragment = new FragmentIconProfile();
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
        View view=inflater.inflate(R.layout.fragment_icon_profile, container, false);

        Button button=view.findViewById(R.id.btn_log_out);

        SharedPreferences shPref=getContext().getSharedPreferences("shPrefData",0);
        int user_id=shPref.getInt("user_id",0);

        SoniCarDB db=SoniCarDB.getInstance(getContext().getApplicationContext());
        final User user=db.getUsersDAO().get(user_id);

        TextView tv_lastname=view.findViewById(R.id.tv_lastname);
        tv_lastname.setText(user.getLastname());


        TextView tv_firstname=view.findViewById(R.id.tv_firstname);
        tv_firstname.setText(user.getFirstname());

        TextView tv_username=view.findViewById(R.id.tv_username);
        tv_username.setText(user.getUsername());

        TextView tv_birthdate=view.findViewById(R.id.tv_birthdate);
        tv_birthdate.setText(user.getBirthdate());

        TextView tv_phone=view.findViewById(R.id.tv_phone);
        tv_phone.setText(user.getPhone());

        TextView tv_email=view.findViewById(R.id.tv_email);
        tv_email.setText(user.getEmail());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //back to log in page
                //delete from shPrefData user_id

                Intent intent=new Intent(getContext().getApplicationContext(),  MainActivity.class);

                SharedPreferences shPrefLog=getContext().getSharedPreferences("shPrefUser",0);
                SharedPreferences.Editor editor=shPrefLog.edit();
                editor.clear();
                editor.apply();

                SharedPreferences shPref=getContext().getSharedPreferences("shPrefData",0);
                SharedPreferences.Editor myEdit=shPref.edit();
                myEdit.clear();
                myEdit.apply();

                startActivity(intent);
            }
        });

        Button btnEdit=view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext().getApplicationContext(),EditProfileActivity.class);
                intent.putExtra("userEdit", user);
                startActivity(intent);

            }
        });
        return view;
    }
}