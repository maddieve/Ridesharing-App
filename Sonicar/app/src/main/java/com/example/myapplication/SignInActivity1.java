package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignInActivity1 extends AppCompatActivity {
    public static final int REQUEST_CODE=200;

    private Intent intent;
    private Button btn_next;
    private EditText lastname;
    private EditText firstname;
    private TextView birthdate;
    private EditText phone;
    private EditText email;
    private User user;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        intent=getIntent();

        birthdate=findViewById(R.id.et_date);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SignInActivity1.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int newMonth=month+1;
                String date = year + "/" + newMonth+ "/" + dayOfMonth;
                birthdate.setText(date);
            }
        };


        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastname=findViewById(R.id.et_lastname);
                firstname=findViewById(R.id.et_firstname);
                phone=findViewById(R.id.et_phone);
                email=findViewById(R.id.et_email);
                birthdate=findViewById(R.id.et_date);



                //add user in sharedPref

                boolean goAhead=true;

                SharedPreferences shPrefUser=getSharedPreferences("shPrefUser",0);
                SharedPreferences.Editor myEditor=shPrefUser.edit();
                myEditor.clear();

                if(!lastname.getText().toString().matches("^[a-zA-Z\\s]+") || lastname.getText().length()<3
                        || lastname.getText().length()>20){

                    goAhead=false;
                    Toast.makeText(getApplicationContext(), "Incorect lastname!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    myEditor.putString("lastname",lastname.getText().toString());

                }

                if(!lastname.getText().toString().matches("^[a-zA-Z\\s]+") || lastname.getText().length()<3
                        || lastname.getText().length()>20){
                    goAhead=false;
                    Toast.makeText(getApplicationContext(), "Incorrect lastname!", Toast.LENGTH_SHORT).show();
                }else
                {
                    myEditor.putString("firstname",firstname.getText().toString());

                }

                myEditor.putString("birthdate",birthdate.getText().toString());

                if(!phone.getText().toString().matches("[0-9]+$") || phone.getText().length() != 10) {
                    goAhead=false;
                    Toast.makeText(getApplicationContext(), "Incorrect phone!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    myEditor.putString("phone",phone.getText().toString());

                }
                 if(!email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+")) {
                     goAhead=false;
                     Toast.makeText(getApplicationContext(),"Incorrect Email!",Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     myEditor.putString("email",email.getText().toString());
                 }
                myEditor.apply();

                Intent intent=new Intent(getApplicationContext(),SignInActivity2.class);
                if(goAhead)
                {
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null) {


        }

    }
}