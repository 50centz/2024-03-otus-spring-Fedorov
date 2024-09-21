package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDto> findAllByBookId(Long id);

    List<CommentDto> findByBookId(long id);

    Optional<CommentDto> findById(long id);

    Comment create(String comment, long bookId);

    void update(long id, String comment);

    void deleteById(long id);

}
