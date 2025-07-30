package com.example.studentinfo;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class dbHelperTest {
    DbHelper db;

    @BeforeEach
    @Test
    public void setUp() {
        db = new DbHelper(null); // for test only, no real DB
    }
}
