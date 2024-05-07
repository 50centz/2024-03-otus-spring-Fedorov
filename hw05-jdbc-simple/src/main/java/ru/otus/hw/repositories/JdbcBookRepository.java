package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Optional<Book> findById(long id) {

        Map<String, Object> params = Collections.singletonMap("id", id);

        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "SELECT books.id, books.title, " +
                            "books.author_id, authors.full_name, " +
                            "books.genre_id, genres.name FROM books " +
                            "INNER JOIN authors ON books.author_id=authors.id " +
                            "INNER JOIN genres ON books.genre_id=genres.id " +
                            "WHERE books.id = :id",
                    params,
                    new BookRowMapper()));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {

        return namedParameterJdbcOperations.query("SELECT books.id, books.title, " +
                "books.author_id, authors.full_name, books.genre_id, genres.name FROM books " +
                "INNER JOIN authors ON books.author_id=authors.id " +
                "INNER JOIN genres ON books.author_id=genres.id", new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {

        namedParameterJdbcOperations.update(
                "DELETE FROM books WHERE id = :id", Map.of("id", id)
        );
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());


        namedParameterJdbcOperations.update("INSERT INTO books (title, author_id, genre_id) " +
                "VALUES (:title, :author_id, :genre_id)", params, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        Optional<Book> checkBook = findById(book.getId());

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД

        if (checkBook.isEmpty()) {
            throw  new EntityNotFoundException(String.format("The book with this id: %s was not found",
                    book.getId()));
        }

        namedParameterJdbcOperations.update("UPDATE books SET title = :title, author_id = :author_id, " +
                "genre_id = :genre_id WHERE id = :id", params);

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("author_id"), rs.getString("full_name"));
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("name"));
            return new Book(id, title, author, genre);
        }
    }
}
