package com.example.studentinfo;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    @Test
    void searchStudents_shouldReturnEmptyListWhenNoResults() {
        List<String[]> emptyList = new ArrayList<>();
        when(mockDbHelper.searchStudents(anyString())).thenReturn(emptyList);
        List<String[]> results = studentService.searchStudents("test");

        assertEquals(0, results.size());
        assertTrue(results.isEmpty());
        @ParameterizedTest
        @ValueSource(strings = {"999", "000", "111"})
        void idExists_shouldReturnFalse_whenIdNotExists (String rollNumber){
            // Arrange
            when(mockDbHelper.idExists(rollNumber)).thenReturn(false);

            // Act
            boolean result = studentService.idExists(rollNumber);

            // Assert
            assertFalse(result);
            verify(mockDbHelper).idExists(rollNumber);
        }
        @Test
        void searchStudents_shouldReturnMultipleStudents () {
            // Arrange
            List<String[]> mockResults = Arrays.asList(
                    new String[]{"123", "Mutahar"},
                    new String[]{"456", "rajon"}
            );
            when(mockDbHelper.searchStudents("J")).thenReturn(mockResults);

            // Act
            List<String[]> results = studentService.searchStudents("J");

            // Assert
            assertEquals(2, results.size());
            assertArrayEquals(new String[]{"123", "Mutahar"}, results.get(0));
            assertArrayEquals(new String[]{"456", "Rahon"}, results.get(1));
        }
        @Test
        @Timeout(1)
        void insertStudent_shouldCompleteQuickly () {
            when(mockDbHelper.insertStudent("019", "Quick User")).thenReturn(true);
            assertTimeout(Duration.ofMillis(500), () -> {
                studentService.insertStudent("019", "Quick User");
            });
        }
        @Test
        void insertStudent_shouldNotThrowException() {
            when(mockDbHelper.insertStudent("232_134_019", "Safe User")).thenReturn(true);
            assertDoesNotThrow(() -> studentService.insertStudent("019", "Safe User"));
        }


    }