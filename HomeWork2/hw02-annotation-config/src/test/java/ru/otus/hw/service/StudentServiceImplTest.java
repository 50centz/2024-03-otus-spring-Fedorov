package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentService = Mockito.mock(StudentServiceImpl.class);
        Mockito.when(studentService.determineCurrentStudent()).thenReturn(new Student("Evgeniy", "Fedorov"));
    }

    @DisplayName("StudentServiceImpl : Method(determineCurrentStudent)")
    @Test
    void shouldHaveCurrentStudentWithMethod() {

        Student student = studentService.determineCurrentStudent();


        assertAll("student",
                () -> assertEquals("Evgeniy", student.firstName()),
                () -> assertEquals("Fedorov", student.lastName()));

    }
}