package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;



    @DisplayName("CommentServiceImpl : Method(findAllCommentByBookId())")
    @Test
    void shouldHaveReturnListCommentByBookIdWithMethod() {
        var actualComment = commentService.findByBookId(1);
        var expectedComment = createListComment();

        assertThat(actualComment).containsExactlyElementsOf(expectedComment);
    }

    @DisplayName("CommentServiceImpl : Method(findById())")
    @Test
    void findById() {
        var actualComment = commentService.findById(1);
        var expectedComment = createComment();

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);

    }

    @DisplayName("CommentServiceImpl : Method(insert())")
    @Test
    void shouldHaveInsertCommentInRepositoryWithMethod() {
        commentService.create("New Comment", 1);

        var actualComment = commentService.findById(4);
        var expectedComment = createCommentForInsertTest();

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);
    }

    @DisplayName("CommentServiceImpl : Method(updateCommentById())")
    @Test
    void updateCommentById() {
        commentService.update(4, "Update Comment");

        var actualComment = commentService.findById(4);
        var expectedComment = createCommentForUpdateTest();

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);

    }

    @DisplayName("CommentServiceImpl : Method(deleteById())")
    @Test
    void deleteById() {

        var comment = commentService.findById(4);

        assertThat(comment).isPresent();

        commentService.deleteById(4);

        var actualComment = commentService.findById(4);

        assertThat(actualComment).isEmpty();
    }

    private List<Comment> createListComment() {

        List<Comment> comments = new ArrayList<>();

        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        Book book = new Book(1L, "BookTitle_1", author, genre);

        comments.add(new Comment(1, "Comment_1", book));
        comments.add(new Comment(4, "New Comment", book));

        return comments;
    }

    private Comment createComment() {

        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        Book book = new Book(1L, "BookTitle_1", author, genre);

        return new Comment(1, "Comment_1", book);

    }

    private Comment createCommentForInsertTest() {
        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        Book book = new Book(1L, "BookTitle_1", author, genre);

        return new Comment(4, "New Comment", book);
    }

    private Comment createCommentForUpdateTest() {
        Author author = new Author(1, "Author_1");
        Genre genre = new Genre(1, "Genre_1");
        Book book = new Book(1L, "BookTitle_1", author, genre);

        return new Comment(4, "Update Comment", book);
    }

}