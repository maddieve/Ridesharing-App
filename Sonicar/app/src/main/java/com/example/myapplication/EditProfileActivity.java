package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final User user= (User) getIntent().getSerializableExtra("userEdit");

        final EditText et_lastname=findViewById(R.id.et_lastname);
        et_lastname.setText(user.getLastname());

        final EditText et_firstname=findViewById(R.id.et_firstname);
        et_firstname.setText(user.getFirstname());

        final EditText et_phone=findViewById(R.id.et_phone);
        et_phone.setText(user.getPhone());

        final EditText et_username=findViewById(R.id.et_username);
        et_username.setText(user.getUsername());

        final EditText et_email=findViewById(R.id.et_email);
        et_email.setText(user.getEmail());

        final TextView et_birthdate=findViewById(R.id.et_birthdate);
        et_birthdate.setText(user.getBirthdate());

       final int user_id=user.getId();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference().
                child("dam-project-users").child("dam-project-users");
        myRef.keepSynced(true);

        SoniCarDB db=SoniCarDB.getInstance(getApplicationContext());
        final UsersDAO usersDAO=db.getUsersDAO();

        et_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }

        });

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int newMonth=month+1;
                        String date = year + "/" + newMonth+ "/" + dayOfMonth;
                        et_birthdate.setText(date);
                    }
                };


        Button btn_appy_changes=findViewById(R.id.btn_apply_changes);
        btn_appy_changes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final ValueEventListener listener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            for (DataSnapshot ds : snapshot.getChildren()) {

                                User userFB = ds.getValue(User.class);
                                if (user_id == userFB.getId())
                                {
                                    DatabaseReference ref=ds.getRef();

                                    if(!lastnameChanged(userFB,et_lastname))
                                    {
                                        ref.child("lastname").setValue(et_lastname.getText().toString());

                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                usersDAO.updateLN(et_lastname.getText().toString(),user_id);

                                            }
                                        });
                                    }
                                    if(!firstnameChanged(userFB,et_firstname))
                                    {
                                        ref.child("firstname").setValue(et_firstname.getText().toString());
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                usersDAO.updateFN(et_firstname.getText().toString(),user_id);

                                            }
                                        });

                                    }
                                    if(!usernameChanged(userFB,et_username))
                                    {
                                        ref.child("username").setValue(et_username.getText().toString());
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                usersDAO.updateUN(et_username.getText().toString(),user_id);

                                            }
                                        });

                                    }
                                    if(!birthdateChanged(userFB,et_birthdate))
                                    {
                                        ref.child("birthdate").setValue(et_birthdate.getText().toString());
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                usersDAO.updateBD(et_birthdate.getText().toString(),user_id);

                                            }
                                        });

                                    }
                                    if(!phoneChanged(userFB,et_phone))
                                    {
                                        ref.child("phone").setValue(et_phone.getText().toString());
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                usersDAO.updatePH(et_phone.getText().toString(),user_id);

                                            }
                                        });

                                    }
                                    if(!emailChanged(userFB,et_email))
                                    {
                                        ref.child("email").setValue(et_email.getText().toString());
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                usersDAO.updateEM(et_email.getText().toString(),user_id);

                                            }
                                        });

                                    }

                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                myRef.addValueEventListener(listener);

                Toast.makeText(getApplicationContext(),"Ai modificat cu succes!",Toast.LENGTH_SHORT).show();

            }


        });

    }

    private boolean lastnameChanged(User user, EditText et)
    {
        return user.getLastname().equals(et.getText().toString());
    }
    private boolean firstnameChanged(User user, EditText et)
    {
        return user.getFirstname().equals(et.getText().toString());
    }
    private boolean usernameChanged(User user, EditText et)
    {
        return user.getUsername().equals(et.getText().toString());
    }
    private boolean phoneChanged(User user, EditText et)
    {
        return user.getPhone().equals(et.getText().toString());
    }
    private boolean emailChanged(User user, EditText et)
    {
        return user.getEmail().equals(et.getText().toString());
    }
    private boolean birthdateChanged(User user, TextView et)
    {
        return user.getBirthdate().toString().equals(et.getText().toString());
    }
}