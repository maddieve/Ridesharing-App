package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    private Button btn;
    private static final int REQUEST_CODE=200;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SharedPreferences shPref=getSharedPreferences("shPrefData",0);
        SharedPreferences.Editor myEdit=shPref.edit();
        myEdit.clear();
        myEdit.apply();

        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=findViewById(R.id.et_email);
                password=findViewById(R.id.et_password);

                //search for the user with this username and this password

                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference dbRef=database.getReference("dam-project-users");
                ValueEventListener listener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot dn: snapshot.getChildren())
                            {
                                User user=dn.getValue(User.class);

                                if(email.getText().toString().equals(user.getEmail()) &&
                                        password.getText().toString().equals(user.getPassword()))
                                {
                                    //user is logged in
                                    //create shPref file with info user
                                    SharedPreferences shPrefUserLog=getSharedPreferences("shPrefData",0);
                                    SharedPreferences.Editor myEditor=shPrefUserLog.edit();
                                    myEditor.putInt("user_id",user.getId());
                                    myEditor.apply();

                                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                    startActivity(intent);
                                }
                                if(email.getText().toString().equals(user.getEmail()) &&
                                        !password.getText().toString().equals(user.getPassword())){

                                    Toast.makeText(getApplicationContext(),"Incorrect data!", Toast.LENGTH_SHORT).show();
                                }

                                if(!email.getText().toString().equals(user.getEmail()) &&
                                        password.getText().toString().equals(user.getPassword())) {
                                    Toast.makeText(getApplicationContext(), "Incorrect data!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                dbRef.child("dam-project-users").addValueEventListener(listener);



            }
        });
    }

}