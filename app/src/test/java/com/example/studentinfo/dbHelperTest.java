package com.example.studentinfo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class dbHelperTest {
    DbHelper db;

    @BeforeEach
    @Test
    public void setUp() {
        db = new DbHelper(null);
    }
    @ParameterizedTest
    @ValueSource(strings = {"888", "999", "1000"})
    void testValueSourceInsert(String id) {
        boolean inserted = db.insertStudent(id, "Dummy");
        assertTrue(inserted);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/students.csv", numLinesToSkip = 1)
    void testCsvFileSource(String id, String name) {
        assertTrue(db.insertStudent(id, name));
    }
}
