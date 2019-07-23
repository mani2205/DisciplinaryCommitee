package com.example.dc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    private EditText name,phone,email,username,password,confirmpass;
    Button signup;
    DatabaseReference reff;
    UserPojo obj;
    Intent i;
    int a;
 String usernameS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.txtName);
        phone=findViewById(R.id.txtPhone);
        email=findViewById(R.id.txtEmail);
        username=findViewById(R.id.txtUsername);
        password=findViewById(R.id.txtPassword);
        confirmpass=findViewById(R.id.txtConfirmPassword);
        signup=findViewById(R.id.btnSignUp);
        obj=new UserPojo();

        reff= FirebaseDatabase.getInstance().getReference().child("User");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameS=name.getText().toString().trim();
                final String phoneS=phone.getText().toString().trim();
                final String emailS=email.getText().toString().trim();
                usernameS=username.getText().toString().trim();
                final String passwordS=password.getText().toString().trim();
                final String confirmpassS=confirmpass.getText().toString().trim();
                if(TextUtils.isEmpty(nameS))
                {
                    name.setError("please enter Name");
                    return;
                }
                if(TextUtils.isEmpty(phoneS))
                {
                    phone.setError("please enter Phone Number");
                    return;
                }
                if(TextUtils.isEmpty(emailS))
                {
                    email.setError("please enter Email address");
                    return;
                } if(TextUtils.isEmpty(usernameS))
                {
                    username.setError("please enter User Name");
                    return;
                } if(TextUtils.isEmpty(passwordS))
                {
                    password.setError("please enter Password");
                    return;
                } if(TextUtils.isEmpty(confirmpassS))
                {
                    confirmpass.setError("please Confirm your password");
                    return;
                }
                if(!passwordS.equals(confirmpassS))
                {
                    confirmpass.setError("password and confirm password must be equal");
                    return;
                }
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(usernameS).exists())
                                {
                                    username.setError("User name alredy exixts");
                                    return;
                                }
                                else
                                {
                            obj.setName(nameS);
                            obj.setPhone(phoneS);
                            obj.setEmail(emailS);
                            obj.setUsername(usernameS);
                            obj.setPassword(passwordS);

                            reff.child(usernameS).setValue(obj);
                            Toast.makeText(SignUp.this, "Sucessfully added..", Toast.LENGTH_LONG).show();
                            i = new Intent(SignUp.this, MainActivity.class);
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

}
