package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private QuestionDao questionDao;

    @BeforeEach
    public void setUp() {

        Mockito.when(ioService.readIntForRangeWithPromptLocalized(1, 3,
                "TestService.answer.option",
                "TestService.answer.error.message")).thenReturn(2);

        given(questionDao.findAll()).willReturn(getQuestions());
    }

    @DisplayName("TestServiceImpl : Method(executeTestFor)")
    @Test
    void shouldHaveCreateTestResultWithMethod() {
        TestService testService = new TestServiceImpl(ioService, questionDao);
        Student student = new Student("Evgeniy", "Fedorov");

        TestResult testResult = testService.executeTestFor(student);


        assertAll("testResult",
                () -> assertNotNull(testResult),
                () -> assertEquals(3, testResult.getAnsweredQuestions().size()),
                () -> assertEquals("Evgeniy Fedorov", testResult.getStudent().getFullName()));
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("Is there life on Mars?", Arrays.asList(
                new Answer("Science doesn't know this yet", true),
                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                new Answer("Absolutely not", false)
        )));

        questions.add(new Question("How should resources be loaded form jar in Java?", Arrays.asList(
                new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true),
                new Answer("ClassLoader#geResource#getFile + FileReader", false),
                new Answer("Wingardium Leviosa", false)
        )));

        questions.add(new Question("Which option is a good way to handle the exception?", Arrays.asList(
                new Answer("@SneakyThrow", false),
                new Answer("e.printStackTrace()", false),
                new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true)
        )));

        return questions;
    }
}