package ru.otus.hw.services;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

import java.util.List;

public interface BookService {
    Book findById(Long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(Book book);

    void deleteById(long id, Book book);
}
