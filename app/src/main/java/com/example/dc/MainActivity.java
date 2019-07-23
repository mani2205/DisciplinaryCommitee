package com.example.dc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//This is a first page and login page..

public class MainActivity extends AppCompatActivity {
Intent i;
Bundle bundle;
private Button btnSignup,btnLogin;
private EditText username,password;
private String username_string,password_string;

DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.aa);
        password=findViewById(R.id.bb);

        btnLogin=findViewById(R.id.loginBtn);
        btnSignup=findViewById(R.id.signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_string=username.getText().toString().trim();
                password_string=password.getText().toString().trim();
                if(username_string.isEmpty())
                {
                    username.setError("Please Enter User name !");
                    return;
                }
                if(password_string.isEmpty())
                {
                    password.setError("Please Enter Password !");
                    return;
                }
                reff= FirebaseDatabase.getInstance().getReference().child("User");
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username_string).exists())
                        {
                            reff= FirebaseDatabase.getInstance().getReference().child("User").child(username_string);
                            reff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {

                                    String username_db=dataSnapshot.child("username").getValue().toString();
                                    String password_db=dataSnapshot.child("password").getValue().toString();
                                    String name_db=dataSnapshot.child("name").getValue().toString();
                                    String Email_db=dataSnapshot.child("email").getValue().toString();
                                    bundle=new Bundle();
                                    bundle.putString("name",name_db);
                                    bundle.putString("email",Email_db);

                                    Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_LONG).show();
                                    i=new Intent(MainActivity.this,NavMain.class);
                                    i.putExtras(bundle);

                                    if(username_db.equals(username_string)&&password_db.equals(password_string))
                                    {
                                        startActivity(i);
                                        username.setText("");
                                        password.setText("");
                                    }
                                    else
                                    {

                                       password.setError("Incorrect password !");
                                    }

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            username.setError("Incorrect user name");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });
    }
}
