package ru.otus.hw.repositories;

import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    void deleteAllByBookId(String id);

    List<Comment> findAllByBookId(String id);
}
