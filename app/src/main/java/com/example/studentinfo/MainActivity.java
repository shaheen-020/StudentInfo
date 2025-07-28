package com.example.studentinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnView, btnUpdate, btnStdRecords, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Initialize all buttons
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnStdRecords = findViewById(R.id.btnStdRecords);

        // ✅ Set click listeners to navigate to corresponding activities
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewStudentsActivity.class);
            startActivity(intent);
        });

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UpdateStudentActivity.class);
            startActivity(intent);
        });

        btnStdRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentRecordsActivity.class);
            startActivity(intent);
        });


    }
}
