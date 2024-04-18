package ru.otus.hw.dao;

import ru.otus.hw.domain.Question;

import java.io.FileNotFoundException;
import java.util.List;

public interface QuestionDao {
    List<Question> findAll() throws FileNotFoundException;
}
