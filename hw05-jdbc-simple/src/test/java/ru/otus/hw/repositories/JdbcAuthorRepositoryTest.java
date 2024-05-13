package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import(JdbcAuthorRepository.class)
class JdbcAuthorRepositoryTest {

    @Autowired
    private JdbcAuthorRepository jdbcAuthorRepository;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }


    @DisplayName("JdbcAuthorRepository : Method(findAll())")
    @Test
    void shouldHaveCreateListAuthorByIdWithMethod() {
        var actualAuthors = jdbcAuthorRepository.findAll();
        var expectedAuthor = dbAuthors;

        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthor);
        actualAuthors.forEach(System.out::println);

    }

    @DisplayName("JdbcAuthorRepository : Method(findById(long id))")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldHaveReturnCorrectAuthorByIdWithMethod(Author author) {

        var actualAuthor = jdbcAuthorRepository.findById(author.getId());

        assertThat(actualAuthor).isPresent().get().isEqualTo(author);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

}