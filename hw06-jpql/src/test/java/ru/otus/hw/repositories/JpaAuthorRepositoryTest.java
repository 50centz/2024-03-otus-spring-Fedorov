package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuthorRepository.class)
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @DisplayName("JpaAuthorRepository : Method(findAll())")
    @Test
    void shouldHaveCreateListAuthorByIdWithMethod() {
        var actualAuthors = jpaAuthorRepository.findAll();
        var expectedAuthor = dbAuthors;

        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthor);
        actualAuthors.forEach(System.out::println);
    }

    @DisplayName("JpaAuthorRepository : Method(findById(long id))")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldHaveReturnCorrectAuthorByIdWithMethod(Author author) {
        var actualAuthor = jpaAuthorRepository.findById(author.getId());

        assertThat(actualAuthor).isPresent().get().isEqualTo(author);
    }

    @DisplayName("JpaAuthorRepository : Method(save())")
    @Test
    void shouldHaveSaveNewAuthorWithMethod() {
        var expectedAuthor = saveNewAuthor();
        var actualAuthor = jpaAuthorRepository.findById(4);

        assertThat(actualAuthor).isPresent().get().isEqualTo(expectedAuthor);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private Author saveNewAuthor() {
        return jpaAuthorRepository.save(new Author(0, "Author_4"));
    }
}