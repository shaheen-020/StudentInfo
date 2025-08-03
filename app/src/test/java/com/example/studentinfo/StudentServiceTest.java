package com.example.studentinfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
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
        // Arrange
        when(mockDbHelper.idExists(rollNumber)).thenReturn(false);

        // Act
        boolean result = studentService.idExists(rollNumber);

        // Assert
        assertFalse(result);
        verify(mockDbHelper).idExists(rollNumber);
    }

}