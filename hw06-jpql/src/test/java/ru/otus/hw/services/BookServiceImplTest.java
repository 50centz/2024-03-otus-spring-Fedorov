package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }


    @DisplayName("BookServiceImpl : Method(findById())")
    @Test
    void shouldHaveReturnCorrectBookByIdWithMethod() {
        Book expectedBook = createBook();

        var actualBook = bookServiceImpl.findById(1);

        assertThat(actualBook).isPresent().get().isEqualTo(expectedBook);
    }


    @DisplayName("BookServiceImpl : Method(findAll())")
    @Test
    void shouldHaveCreateListBooksByIdWithMethod() {
        bookServiceImpl.update(1, "BookTitle_1", 1, 1);
        var actualBooks = bookServiceImpl.findAll();
        var expectedBooks = dbBooks;

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
    }

    @DisplayName("BookServiceImpl : Method(insert())")
    @Test
    void shouldHaveInsertBooksInRepositoryWithMethod() {

        bookServiceImpl.insert("BookTitle_4", 1, 1);

        var actualBook = bookServiceImpl.findById(4);
        var expectedBook = createNewBook();

        assertThat(actualBook).isPresent().get().isEqualTo(expectedBook);
    }

    @DisplayName("BookServiceImpl : Method(update())")
    @Test
    void shouldHaveUpdateBooksInRepositoryWithMethod() {

        bookServiceImpl.update(1, "New Text", 1, 1);

        var actualBook = bookServiceImpl.findById(1);

        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setTitle("New Text");
        expectedBook.setAuthor(new Author(1, "Author_1"));
        expectedBook.setGenre(new Genre(1, "Genre_1"));

        assertThat(actualBook).isPresent().get().isEqualTo(expectedBook);

    }

    @DisplayName("BookServiceImpl : Method(deleteById())")
    @Test
    void shouldHaveDeleteBookByIdWithMethod() {

        var book = bookServiceImpl.findById(4);

        assertThat(book).isPresent();

        bookServiceImpl.deleteById(4);

        var actualBook = bookServiceImpl.findById(4);

        assertThat(actualBook).isEmpty();
    }

    private Book createBook(){
        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        return new Book(1, "New Text", author, genre);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 5).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 5).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 5).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }

    private Book createNewBook(){
        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        return new Book(4, "BookTitle_4", author, genre);
    }

}