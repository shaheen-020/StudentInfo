package com.example.studentinfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private DbHelper mockDbHelper;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(mockDbHelper);
    }
    @ParameterizedTest
    @ValueSource(strings = {"123", "456", "789"})
    void idExists_shouldReturnTrue_whenIdExists(String rollNumber) {
        when(mockDbHelper.idExists(rollNumber)).thenReturn(true);
        boolean result = studentService.idExists(rollNumber);
        assertTrue(result);
        verify(mockDbHelper).idExists(rollNumber);
    }
    @ParameterizedTest
    @ValueSource(strings = {"999", "000", "111"})
    void idExists_shouldReturnFalse_whenIdNotExists(String rollNumber) {

        when(mockDbHelper.idExists(rollNumber)).thenReturn(false);
        boolean result = studentService.idExists(rollNumber);
        assertFalse(result);
        verify(mockDbHelper).idExists(rollNumber);
    }
    @ParameterizedTest
    @CsvSource({
            "123, John Doe, true",
            "456, Jane Smith, true",
            "789, Alice Johnson, false"
    })
    void insertStudent_shouldReturnExpectedResult(String roll, String name, boolean expectedResult) {
        when(mockDbHelper.insertStudent(roll, name)).thenReturn(expectedResult);
        boolean result = studentService.insertStudent(roll, name);
        assertEquals(expectedResult, result);
        verify(mockDbHelper).insertStudent(roll, name);
    }

}