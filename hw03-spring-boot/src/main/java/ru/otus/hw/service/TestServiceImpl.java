package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = getAnswer(question); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean getAnswer(Question question) {

        int optional = 1;

        ioService.printLine(question.text() + "\n");

        for (Answer answer : question.answers()) {
            ioService.printLine(optional + " " + ioService.getMessage("TestService.optional") + ". "
                    + answer.text() + "\n");
            ++optional;
        }

        ioService.printLine("");

        boolean answer = getAnswerOption(question.answers());
        ioService.printLine("****************************************************************");

        return answer;
    }

    private boolean getAnswerOption(List<Answer> listAnswers) {
        String message = ioService.getMessage("TestService.answer.option");
        String errorMessage = ioService.getMessage("TestService.answer.error.message");
        int numberAnswer = ioService.readIntForRangeWithPrompt(1, listAnswers.size(), message,
                errorMessage);

        return getBooleanAnswer(listAnswers, numberAnswer);
    }

    private boolean getBooleanAnswer(List<Answer> answerList, int numberAnswer) {
        Answer answer = answerList.get(numberAnswer - 1);
        return answer.isCorrect();
    }

}
