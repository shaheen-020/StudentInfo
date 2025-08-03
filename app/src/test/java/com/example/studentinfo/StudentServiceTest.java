package com.example.studentinfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
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
    @ParameterizedTest
    @CsvSource({
            "'', Name, false",
            "123, '', false",
            "'', '', false"
    })
    void insertStudent_shouldHandleEmptyParameters(String roll, String name, boolean expectedResult) {
        when(mockDbHelper.insertStudent(roll, name)).thenReturn(expectedResult);
        boolean result = studentService.insertStudent(roll, name);
        assertEquals(expectedResult, result);
        verify(mockDbHelper).insertStudent(roll, name);
    }

    @Test
    void insertStudent_shouldPassCorrectParametersToDbHelper() {
        String testRoll = "020 ";
        String testName = "Shaheen Rashid ";
        when(mockDbHelper.insertStudent(anyString(), anyString())).thenReturn(true);
        boolean result = studentService.insertStudent(testRoll, testName);
        assertTrue(result);
        verify(mockDbHelper).insertStudent(eq(testRoll), eq(testName));
    }
    @Test
    void studentService_shouldNotBeNull() {
        assertNotNull(studentService); // ensures service is initialized
    }
    @Test
    void testNull() {
        when(mockDbHelper.searchStudents("unknown")).thenReturn(null);
        List<String[]> result = studentService.searchStudents("unknown");
        assertNull(result, "Expected result to be null when DB returns null");
    }
    @Test
    void testNotNull() {
        List<String[]> dummyData = new ArrayList<>();
        dummyData.add(new String[]{"101", "Shahinur"});
        when(mockDbHelper.searchStudents("101")).thenReturn(dummyData);
        List<String[]> result = studentService.searchStudents("101");
        assertNotNull(result, "Expected result to be non-null when DB returns list");
    }
    @Test
    void testAssertEqual() {
        when(mockDbHelper.insertStudent("303", "shaheen")).thenReturn(true);
        boolean result = studentService.insertStudent("303", "shaheen");
        assertEquals(true, result, "Student should be inserted successfully");
    }
    @Test
    void testAssertNotEquals() {
        when(mockDbHelper.insertStudent("404", "Ali")).thenReturn(true);
        boolean result = studentService.insertStudent("404", "Ali");
        assertNotEquals(false, result, "Result should not be false if DB returns true");
    }
    @Test
    void testAssertThrows() {
        when(mockDbHelper.insertStudent(null, "Ali")).thenThrow(new IllegalArgumentException("Roll can't be null"));
        assertThrows(IllegalArgumentException.class, () -> studentService.insertStudent(null, "Ali"));
    }
    @Test
    void testAssertDoesNotThrow() {
        when(mockDbHelper.insertStudent("500", "Fatema")).thenReturn(true);
        assertDoesNotThrow(() -> studentService.insertStudent("500", "Fatema"), "Valid input should not throw");
    }
}