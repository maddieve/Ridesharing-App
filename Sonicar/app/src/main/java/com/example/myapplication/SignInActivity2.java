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

import java.util.Date;

public class SignInActivity2 extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private FirebaseDatabase dataBase;
    private Button btn_save;


    private void salvareUserInFirebase(final User user){

        dataBase=FirebaseDatabase.getInstance();
        final DatabaseReference myRef=dataBase.getReference("dam-project-users");
        myRef.keepSynced(true);

        myRef.child("dam-project-users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //insert
                user.setUid(myRef.child("dam-project-users").push().getKey());
                //atasez un nod copil
                myRef.child("dam-project-users").child(user.getUid()).setValue(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_2);

        btn_save=findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean goAhead=true;

                username=findViewById(R.id.et_username);

                String uname=null;
                String pass=null;
                if(!username.getText().toString().matches("^[a-zA-Z\\s]+") || username.getText().length()<3
                        || username.getText().length()>20) {

                    goAhead=false;
                    Toast.makeText(getApplicationContext(), "Invalid username!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uname=username.getText().toString();

                }
                password=findViewById(R.id.et_password);
                if(password.getText().toString().length()<8 )
                {
                    goAhead=false;
                    Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pass=password.getText().toString();
                }


                SharedPreferences shPrefUser=getSharedPreferences("shPrefUser",0);

              String lastname=shPrefUser.getString("lastname",null);
              String firstname=shPrefUser.getString("firstname",null);
              String email=shPrefUser.getString("email",null);
              String birthdate=shPrefUser.getString("birthdate",null);
             String phone=shPrefUser.getString("phone",null);



                //add user in Room
                SoniCarDB db=SoniCarDB.getInstance(getApplicationContext());

                if(goAhead)
                {
                    User user=new User(firstname,lastname,birthdate,phone,email, uname, pass);
                    db.getUsersDAO().insert(user);//id=1, uid=null

                    //select max user id
                    int maxUserId=db.getUsersDAO().maxUserId();
                    user.setId(maxUserId);

                    //add user in Firebase
                    salvareUserInFirebase(user);//id=0, uid!=null

                    Intent intent=new Intent(getApplicationContext(),ProgressActivity.class);
                    startActivity(intent);

                }


            }
        });
    }

}