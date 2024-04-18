package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
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


    @DisplayName("CsvQuestionDao : Method(findAll) size List")
    @Test
    void shouldHaveCreateListSizeThreeWithMethod() {

        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);

        List<Question> questions = csvQuestionDao.findAll();
        Question question = questions.get(0);
        Answer answers = question.answers().get(0);
        System.out.println(answers.text());

        assertEquals(3, questions.size());
    }


    @DisplayName("CsvQuestionDao : Method(findAll) check answers")
    @Test
    void shouldHaveCheckFirstAnswerWithMethod() {

        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);

        List<Question> questions = csvQuestionDao.findAll();
        Question question = questions.get(0);


        assertAll("question",
                () -> assertEquals("Is there life on Mars?", question.text()),
                () -> assertEquals(3, question.answers().size()),
                () -> assertEquals("Science doesn't know this yet", question.answers().get(0).text()),
                () -> assertTrue(question.answers().get(0).isCorrect()),
                () -> assertEquals("Certainly. The red UFO is from Mars. And green is from Venus",
                        question.answers().get(1).text()),
                () -> assertFalse(question.answers().get(1).isCorrect()),
                () -> assertEquals("Absolutely not", question.answers().get(2).text()),
                () -> assertFalse(question.answers().get(2).isCorrect()));

    }
}