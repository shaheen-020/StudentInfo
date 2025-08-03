package com.example.studentinfo;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
public class StudentService {
    private final DbHelper db;
    // Constructor with dependency injection
    public StudentService(DbHelper dbHelper) {
        this.db = dbHelper;
    }
    public boolean idExists(String roll) {
        return db.idExists(roll);
    }
    public boolean insertStudent(String roll, String name) {
        return db.insertStudent(roll, name);
    }
    public List<String[]> searchStudents(String query) {
        List<String[]> students = new ArrayList<>();
       students = db.searchStudents(query);

        return students;
    }

}