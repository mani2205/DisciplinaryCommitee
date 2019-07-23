package com.example.dc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Calendar;

public class Complaint extends AppCompatActivity {

    Intent intent;
    TextView dateSelect;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText regNo,name,issue;
    Button saveBtn;
    long val=0;
    private String regno_string,name_string,issue_string;
    private int mYear, mMonth, mDay;
    private String curentDate;
    DatabaseReference dr;
    ComplaintPojo obj1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        dateSelect=findViewById(R.id.selectDate);
        regNo=findViewById(R.id.stRegno);
        name=findViewById(R.id.stName);
        issue=findViewById(R.id.stIssue);
        saveBtn=findViewById(R.id.saveComplaint);

        obj1=new ComplaintPojo();
        dr= FirebaseDatabase.getInstance().getReference().child("Complaint");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    val=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regno_string=regNo.getText().toString().trim();
                name_string=name.getText().toString().trim();
                issue_string=issue.getText().toString().trim();
                if(regno_string.isEmpty())
                {
                    regNo.setError("Please enter Register number");
                    return;
                }
                if(name_string.isEmpty())
                {
                    name.setError("Please enter Name");
                    return;
                }
                if(issue_string.isEmpty())
                {
                    issue.setError("Please enter What the issue is happen");
                    return;
                }

                obj1.setDate(curentDate);
                obj1.setRegno(regno_string);
                obj1.setName(name_string);
                obj1.setIssue(issue_string);
                dr.child(regno_string).child(String.valueOf(val)).setValue(obj1);
                Toast.makeText(Complaint.this, "Sucessfully added...", Toast.LENGTH_LONG).show();
                regNo.setText("");
                name.setText("");
                issue.setText("");
                dateSelect.setText("");

            }
        });
    }

    public void selectDate(View view)
    {
        if(view.getId()==R.id.dateBtn)
        {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            dateSelect.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            curentDate=dateSelect.getText().toString();

        }
    }
}
