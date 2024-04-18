package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionDaoTest {



    @DisplayName("CsvQuestionDao : Method(findAll)")
    @Test
    void shouldHaveCreateListQuestionWithMethod() {
        TestFileNameProvider testFileNameProvider = new AppProperties("questions.csv");
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);

        List<Question> questions = csvQuestionDao.findAll();

        assertNotNull(questions);
    }

}