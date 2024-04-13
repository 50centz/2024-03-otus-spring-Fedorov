package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов

        int option = 1;
        try {
            List<Question> questionList = questionDao.findAll();
            for (Question question : questionList) {
                ioService.printFormattedLine(question.text());
                for (Answer answer : question.answers()) {
                    ioService.printLine(option + " Option." + " " + answer.text());
                    ioService.printLine(String.valueOf(answer.isCorrect()));
                    ++option;
                }
                option = 1;
                ioService.printLine("");
            }
        } catch (Exception e) {
            ioService.printLine(e.getMessage());
        }
    }
}
