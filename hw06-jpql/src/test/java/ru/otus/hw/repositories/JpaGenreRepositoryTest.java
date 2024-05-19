package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository jpaGenreRepository;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @DisplayName("JpaGenreRepository : Method(findAll())")
    @Test
    void shouldHaveCreateListGenreByIdWithMethod() {
        var actualGenres = jpaGenreRepository.findAll();
        var expectedGenre = dbGenres;

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenre);
        actualGenres.forEach(System.out::println);
    }

    @DisplayName("JpaGenreRepository : Method(findById(long id))")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    void shouldHaveReturnCorrectGenreByIdWithMethod(Genre genre) {
        var actualGenre = jpaGenreRepository.findById(genre.getId());

        assertThat(actualGenre).isPresent().get().isEqualTo(genre);
    }

    @DisplayName("JpaGenreRepository : Method(save())")
    @Test
    void shouldHaveSaveNewAuthorWithMethod() {
        var expectedGenre = saveNewGenre();
        var actualGenre = jpaGenreRepository.findById(4);

        assertThat(actualGenre).isPresent().get().isEqualTo(expectedGenre);
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private Genre saveNewGenre() {
        return jpaGenreRepository.save(new Genre(0, "Genre_4"));
    }
}