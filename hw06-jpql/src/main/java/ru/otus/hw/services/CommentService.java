package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllCommentByBookId(long id);

    Optional<Comment> findById(long id);

    Comment insert(String comment, long bookId);

    void updateCommentById(long id, String comment);

    void deleteById(long id);

}
