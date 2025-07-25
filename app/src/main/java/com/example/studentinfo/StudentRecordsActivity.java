package com.example.studentinfo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentRecordsActivity extends AppCompatActivity {

    TextView tvName, tvRoll, tvDob, tvDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_record);

        tvName = findViewById(R.id.tvName);
        tvRoll = findViewById(R.id.tvRoll);
        tvDob = findViewById(R.id.tvDob);
        tvDept = findViewById(R.id.tvDept);

        // Get data from intent
        String name = getIntent().getStringExtra("name");
        String roll = getIntent().getStringExtra("roll");
        String dob = getIntent().getStringExtra("dob");
        String dept = getIntent().getStringExtra("dept");

        // Set values
        tvName.setText("Name: " + name);
        tvRoll.setText("Roll No: " + roll);
        tvDob.setText("Date of Birth: " + dob);
        tvDept.setText("Department: " + dept);
    }
}
