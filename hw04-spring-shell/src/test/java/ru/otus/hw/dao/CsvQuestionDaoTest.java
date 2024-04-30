package ru.otus.hw.dao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CsvQuestionDao.class)
class CsvQuestionDaoTest {

    @MockBean
    private TestFileNameProvider testFileNameProvider;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void setUp() {
        given(testFileNameProvider.getTestFileName()).willReturn("questions.csv");
    }

    @DisplayName("CsvQuestionDao : Method(findAll)")
    @Test
    void shouldHaveCreateListQuestionWithMethod() {

        List<Question> questions = csvQuestionDao.findAll();

        assertNotNull(questions);
    }

    @DisplayName("CsvQuestionDao : Method(findAll) size List")
    @Test
    void shouldHaveCreateListSizeThreeWithMethod() {

        List<Question> questions = csvQuestionDao.findAll();
        Question question = questions.get(0);
        Answer answers = question.answers().get(0);
        System.out.println(answers.text());

        assertEquals(3, questions.size());
    }


    @DisplayName("CsvQuestionDao : Method(findAll) check answers")
    @Test
    void shouldHaveCheckFirstAnswerWithMethod() {

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