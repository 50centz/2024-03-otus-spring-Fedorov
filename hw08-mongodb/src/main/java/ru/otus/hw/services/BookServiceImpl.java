package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final BookMapper bookMapper;


    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(String id) {

        return Optional.of(bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {

        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }


    @Transactional
    @Override
    public BookDto create(String title, String genreId, String... authorId) {

        List<Author> authors = new ArrayList<>();

        for (String author : authorId) {
            authors.add(authorRepository.findById(author)
                    .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(author))));
        }
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %s not found".formatted(genreId)));

        return bookMapper.toDto(bookRepository.save(new Book(getId(), title, genre, authors)));
    }

    @Transactional
    @Override
    public BookDto update(String id, String title, String genreId, String... authorId) {

        List<Author> authors = new ArrayList<>();

        for (String author : authorId) {
            authors.add(authorRepository.findById(author)
                    .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(author))));
        }
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %s not found".formatted(genreId)));
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Book with id %s not found".formatted(id)));

        book.setTitle(title);
        book.setGenre(genre);
        book.setAuthors(authors);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        commentRepository.deleteAllByBookId(id);
        bookRepository.deleteById(id);
    }

    private String getId() {
        Optional<Book> book = bookRepository.findAll().stream().reduce((b1, b2) -> b2);

        if (book.isPresent()) {
            String number = book.get().getId();
            int i = Integer.parseInt(number);
            i++;
            return Integer.toString(i);
        }

        return "1";
    }
}
