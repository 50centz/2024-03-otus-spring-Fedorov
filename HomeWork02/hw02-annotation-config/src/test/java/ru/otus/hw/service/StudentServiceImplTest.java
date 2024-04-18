package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    @DisplayName("StudentServiceImpl : Method(determineCurrentStudent)")
    @Test
    void shouldHaveCreateObjectStudentWithMethod() {

        Student student = new Student("Evgeniy", "Fedorov");

        assertEquals("Evgeniy Fedorov", student.getFullName());

    }
}