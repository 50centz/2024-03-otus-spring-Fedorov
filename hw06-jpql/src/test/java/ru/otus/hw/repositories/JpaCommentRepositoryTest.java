package ru.otus.hw.repositories;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCommentRepository.class)
class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository jpaCommentRepository;

    @DisplayName("JpaCommentRepository : Method(findAllCommentByBookId())")
    @Test
    void shouldHaveCreateListCommentWithMethod() {

        List<Comment> actualComment = jpaCommentRepository.findAllCommentByBookId(1);
        Comment expectedComment = getComment();

        assertThat(actualComment.get(0)).isEqualTo(expectedComment);
    }

    @DisplayName("JpaCommentRepository : Method(findById())")
    @Test
    void shouldHaveReturnCommentByIdWithMethod() {
        var actualComment = jpaCommentRepository.findById(1);
        Comment expectedComment = getComment();

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);

    }

    @DisplayName("JpaCommentRepository : Method(save())")
    @Test
    void shouldHaveSaveNewCommentWithMethod() {
        var expectedComment = saveNewComment();
        var actualComment = jpaCommentRepository.findById(4);

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);
    }



    private Comment getComment() {
        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        Book book = new Book(1L, "BookTitle_1", author, genre);

        return new Comment(1, "Comment_1", book);
    }

    private Comment saveNewComment() {
        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        Book book = new Book(1L, "BookTitle_1", author, genre);

        return jpaCommentRepository.save(new Comment(0, "Comment_4", book));
    }
}