package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CsvQuestionDaoTest {

    private TestFileNameProvider testFileNameProvider;

    @BeforeEach
    public void setUp() {
        testFileNameProvider = Mockito.mock(AppProperties.class);
        Mockito.when(testFileNameProvider.getTestFileName()).thenReturn("questions.csv");
    }

    @DisplayName("CsvQuestionDao : Method(findAll)")
    @Test
    void shouldHaveCreateListQuestionWithMethod() {

        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);

        List<Question> questions = csvQuestionDao.findAll();

        assertNotNull(questions);
    }

}