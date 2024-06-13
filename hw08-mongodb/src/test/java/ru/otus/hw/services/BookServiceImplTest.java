package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional(propagation = Propagation.NEVER)
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;


    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<BookDto> dbBooksDto;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooksDto = getDbBooksDto();
    }

    @DisplayName("BookServiceImpl : Method(findById())")
    @Test
    void shouldHaveReturnBookByIdWithMethod() {

        var actualBook = bookService.findById("1").get();
        var expectedBook = createBook();

        assertThat(actualBook)
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("BookServiceImpl : Method(findAll())")
    @Test
    void shouldHaveReturnAllBooksWithMethod() {
        var actualBookDto = bookService.findAll();
        var expectedBook = dbBooksDto;

        assertThat(actualBookDto).containsExactlyElementsOf(expectedBook);
    }

    @DisplayName("BookServiceImpl : Method(create())")
    @Test
    void shouldHaveCreateBookWithMethod() {

        bookService.create("New Book 4", "1", "1");

        var actualBookDto = bookService.findById("4").get();
        var expectedBook = createBookForInsertTest();

        assertThat(actualBookDto)
                .usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("BookServiceImpl : Method(update())")
    @Test
    void shouldHaveUpdateBookWithMethod() {
        bookService.update("3", "New Book", "1", "1");

        var actualBookDto = bookService.findById("3").get();
        var expectedBookDto = createBookForUpdateTest();

        assertThat(actualBookDto)
                .usingRecursiveComparison().isEqualTo(expectedBookDto);
    }

    @DisplayName("BookServiceImpl : Method(deleteById())")
    @Test
    void shouldHaveDeleteBookByIdWithMethod() {
        var bookDto = bookService.findById("2");

        assertThat(bookDto).isPresent();

        bookService.deleteById("2");

        var actualBookDto = bookRepository.findById("2");

        assertThat(actualBookDto).isEmpty();
    }

    private BookDto createBook() {

        List<Author> authors = new ArrayList<>();

        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");

        authors.add(author);

        return bookMapper.toDto(new Book("1", "BookTitle_1", genre, authors));
    }

    private  List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id.toString(), "Author_" + id)).toList();
    }

    private  List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id.toString(), "Genre_" + id)).toList();
    }

    private  List<BookDto> getDbBooksDto() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id.toString(), "BookTitle_" + id, dbGenres.get(id - 1),
                        List.of(dbAuthors.get(id - 1))))
                .map(bookMapper::toDto).toList();
    }

    private BookDto createBookForUpdateTest() {
        return bookMapper.toDto(new Book("3", "New Book", dbGenres.get(0), List.of(dbAuthors.get(0))));
    }

    private BookDto createBookForInsertTest() {
        return bookMapper.toDto(new Book("4", "New Book 4", dbGenres.get(0), List.of(dbAuthors.get(0))));
    }

}