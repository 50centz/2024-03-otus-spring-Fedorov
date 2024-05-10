package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcAuthorRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public List<Author> findAll() {
        return  namedParameterJdbcOperations.query("SELECT id, full_name FROM authors", new AuthorRowMapper());
    }


    @Override
    public Optional<Author> findById(long id) {

        Map<String, Object> params = Collections.singletonMap("id", id);

        List<Author> authors = namedParameterJdbcOperations.query(
                "SELECT id, full_name FROM authors WHERE id = :id", params, new AuthorRowMapper()
        );

        if (authors.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(authors.get(0));
    }


    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
