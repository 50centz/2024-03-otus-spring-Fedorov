package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;


    @Transactional(readOnly = true)
    @Override
    public BookDto findById(String id) {

        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id %s not found".formatted(id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {

        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }


    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {

        List<Author> authors = new ArrayList<>();

        String genreId = bookCreateDto.getGenreId();

        for (String author : bookCreateDto.getAuthorIds()) {
            authors.add(authorRepository.findById(author)
                    .orElseThrow(() -> new NotFoundException("Author with id %s not found".formatted(author))));
        }
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %s not found".formatted(genreId)));

        return bookMapper.toDto(bookRepository.save(new Book(null, bookCreateDto.getTitle(), genre, authors)));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {

        List<Author> authors = new ArrayList<>();

        String genreID = bookUpdateDto.getGenreId();

        for (String author : bookUpdateDto.getAuthorIds()) {
            authors.add(authorRepository.findById(author)
                    .orElseThrow(() -> new NotFoundException("Author with id %s not found".formatted(author))));
        }
        var genre = genreRepository.findById(genreID)
                .orElseThrow(() -> new NotFoundException("Genre with id %s not found".formatted(genreID)));
        Book book = bookRepository.findById(bookUpdateDto.getBookId()).orElseThrow(() -> new NotFoundException(
                "Book with id %s not found".formatted(bookUpdateDto.getBookId())));

        book.setTitle(bookUpdateDto.getTitle());
        book.setGenre(genre);
        book.setAuthors(authors);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.findById(id).orElseThrow(() -> new NotFoundException("The book with this id: %s is not in the DB"
                        .formatted(id)));

        bookRepository.deleteById(id);
    }

}
