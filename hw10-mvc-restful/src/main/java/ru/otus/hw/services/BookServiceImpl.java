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

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final CheckingResponseFromDb checkingResponseFromDb;


    @Override
    public BookDto findById(String id) {

        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id %s not found".formatted(id))));
    }


    @Override
    public List<BookDto> findAll() {

        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }


    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {

        String genreId = bookCreateDto.getGenreId();

        List<Author> authors = authorRepository.findAllById(bookCreateDto.getAuthorIds());

        checkingResponseFromDb.isEmpty(authors, bookCreateDto.getAuthorIds());
        checkingResponseFromDb.checkAuthors(authors, bookCreateDto.getAuthorIds());

        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %s not found".formatted(genreId)));

        return bookMapper.toDto(bookRepository.save(new Book(null, bookCreateDto.getTitle(), genre, authors)));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {

        String genreID = bookUpdateDto.getGenreId();

        List<Author> authors = authorRepository.findAllById(bookUpdateDto.getAuthorIds());

        checkingResponseFromDb.isEmpty(authors, bookUpdateDto.getAuthorIds());
        checkingResponseFromDb.checkAuthors(authors, bookUpdateDto.getAuthorIds());

        var genre = genreRepository.findById(genreID)
                .orElseThrow(() -> new NotFoundException("Genre with id %s not found".formatted(genreID)));

        Book book = bookMapper.toModel(bookUpdateDto, genre, authors);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

}
