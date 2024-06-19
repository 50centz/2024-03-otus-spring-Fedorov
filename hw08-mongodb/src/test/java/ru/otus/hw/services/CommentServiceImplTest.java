package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.mapper.CommentMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional(propagation = Propagation.NEVER)
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("CommentServiceImpl : Method(findAllByBookId())")
    @Test
    void shouldHaveReturnListCommentByBookIdWithMethod() {
        var actualComment = commentService.findAllByBookId("1");
        var expectedComment = createListComment();

        assertThat(actualComment).containsExactlyElementsOf(expectedComment);
    }

    @DisplayName("CommentServiceImpl : Method(findById())")
    @Test
    void shouldHaveReturnCommentByIdWithMethod() {
        var actualComment = commentService.findById("1").get();
        var expectedComment = createComment();

        assertThat(actualComment)
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("CommentServiceImpl : Method(create())")
    @Test
    void shouldHaveCreateCommentInRepositoryWithMethod() {
        commentService.create("New Comment", "1");

        var actualComment = commentService.findById("5").get();
        var expectedComment = createCommentForInsertTest();

        assertThat(actualComment)
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("CommentServiceImpl : Method(update())")
    @Test
    void shouldHaveUpdateCommentByBookIdWithMethod() {
        commentService.update("4", "Update Comment");

        var actualComment = commentService.findById("4").get();
        var expectedComment = createCommentForUpdateTest();

        assertThat(actualComment)
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("CommentServiceImpl : Method(deleteById())")
    @Test
    void shouldHaveDeleteCommentByIdWithMethod() {

        var comment = commentService.findById("2");

        assertThat(comment).isPresent();

        commentService.deleteById("2");

        var actualComment = commentRepository.findById("2");

        assertThat(actualComment).isEmpty();
    }

    private List<CommentDto> createListComment() {

        List<Comment> comments = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");

        authors.add(author);

        Book book = new Book("1", "BookTitle_1", genre, authors);

        comments.add(new Comment("1", "Comment 1", book));
        comments.add(new Comment("4", "Update Comment", book));

        return comments.stream().map(commentMapper::toDto).toList();
    }

    private CommentDto createComment() {

        List<Author> authors = new ArrayList<>();

        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");

        authors.add(author);

        Book book = new Book("1", "BookTitle_1", genre, authors);

        return commentMapper.toDto(new Comment("1", "Comment 1", book));

    }

    private CommentDto createCommentForInsertTest() {

        List<Author> authors = new ArrayList<>();

        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");

        authors.add(author);

        Book book = new Book("1", "BookTitle_1", genre, authors);

        return commentMapper.toDto(new Comment("5", "New Comment", book));
    }

    private CommentDto createCommentForUpdateTest() {
        List<Author> authors = new ArrayList<>();

        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");

        authors.add(author);

        Book book = new Book("1", "BookTitle_1", genre, authors);

        return commentMapper.toDto(new Comment("4", "Update Comment", book));
    }
}