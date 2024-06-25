package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(String id);

    List<BookDto> findAll();

    BookDto create(String title, String genreId, String... authorId);

    BookDto update(String id, String title, String genreId, String... authorId);

    void deleteById(String id);
}
