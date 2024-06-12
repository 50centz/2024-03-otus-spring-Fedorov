package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> findById(String id);

    List<BookDto> findAll();

    BookDto create(String title, String genreId, String... authorId);

    BookDto update(String id, String title, String genreId, String... authorId);

    void deleteById(String id);
}
