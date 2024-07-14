package ru.otus.hw.repositories;

import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Set;

public interface AuthorRepositoryCustom {

    List<Author> findAllById(Set<String> authorIds);
}
