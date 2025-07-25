package com.example.studentinfo;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.database.Cursor;
import android.view.ViewGroup;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity
{
    EditText etName, etRoll;
    Button btnAdd, btnView, btnUpdate, btnStdRecords, btnDelete;
    TableLayout tableLayout;
    dbhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etRoll = findViewById(R.id.etRoll);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnStdRecords = findViewById(R.id.btnStdRecords);
        btnDelete = findViewById(R.id.btnDelete);
        tableLayout = findViewById(R.id.tableLayout);
        db = new dbhelper(this);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String roll = etRoll.getText().toString().trim();
            if (name.isEmpty() || roll.isEmpty()) {
                Toast.makeText(this, "Enter both name and ID", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.idExists(roll)) {
                Toast.makeText(this, "ID already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean added = db.insertStudent(roll, name);
            if (added) {
                Toast.makeText(this, "Student added successful!!", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etRoll.setText("");
            }
        });

        btnView.setOnClickListener(v -> {
            tableLayout.removeAllViews(); // clear old data
            addTableHeader();

            Cursor cursor = db.getAllStudents();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
                return;
            }

            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);
                row.addView(makeTextView(cursor.getString(0)));
                row.addView(makeTextView(cursor.getString(1)));
                tableLayout.addView(row);
            }
            cursor.close();
        });

        btnUpdate.setOnClickListener(v -> {
            String roll = etRoll.getText().toString().trim();
            String name = etName.getText().toString().trim();
            if (roll.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Enter ID and new name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.updateStudent(roll, name)) {
                Toast.makeText(this, "Updated successful!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            String roll = etRoll.getText().toString().trim();
            if (roll.isEmpty()) {
                Toast.makeText(this, "Enter ID to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.deleteStudent(roll)) {
                Toast.makeText(this, "Deleted successful!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addTableHeader() {
        TableRow header = new TableRow(this);
        header.addView(makeTextView("ID", true));
        header.addView(makeTextView("Name", true));
        tableLayout.addView(header);
    }

    TextView makeTextView(String text) {
        return makeTextView(text, false);
    }

    TextView makeTextView(String text, boolean isHeader) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(getResources().getColor(android.R.color.white));
        tv.setPadding(16, 10, 16, 10);
        tv.setGravity(Gravity.CENTER);
        if (isHeader) tv.setTextSize(18);
        return tv;
    }
}