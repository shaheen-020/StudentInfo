package com.example.studentinfo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentRecordsActivity extends AppCompatActivity {

    EditText etQuery;
    Button btnSearch;
    TableLayout tableLayout;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_records); // Make sure this XML file exists

        etQuery = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        tableLayout = findViewById(R.id.tableLayoutRecords);
        db = new DbHelper(this);

        btnSearch.setOnClickListener(v -> {
            String q = etQuery.getText().toString().trim();
            if (q.isEmpty()) {
                Toast.makeText(this, "Enter ID or name", Toast.LENGTH_SHORT).show();
                return;
            }

            tableLayout.removeAllViews(); // clear previous results
            addHeader();

            Cursor c = db.searchStudents(q);
            if (c.getCount() == 0) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
            } else {
                while (c.moveToNext()) {
                    TableRow row = new TableRow(this);
                    row.addView(makeText(c.getString(0))); // ID
                    row.addView(makeText(c.getString(1))); // Name
                    tableLayout.addView(row);
                }
            }
            c.close();
        });
    }

    void addHeader() {
        TableRow header = new TableRow(this);
        header.addView(makeText("ID", true));
        header.addView(makeText("Name", true));
        tableLayout.addView(header);
    }

    TextView makeText(String text) {
        return makeText(text, false);
    }

    TextView makeText(String text, boolean isHeader) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(16, 10, 16, 10);
        if (isHeader) tv.setTextSize(18);
        return tv;
    }
}
