package ru.otus.hw.services;

import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Set;

public interface CheckingResponseFromDb {

    void isEmpty(List<Author> authors, Set<String> authorIds);

    void checkAuthors(List<Author> authors, Set<String> authorIds);
}
