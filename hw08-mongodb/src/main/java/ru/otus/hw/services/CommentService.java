package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentDto> findAllByBookId(String id);

    Optional<CommentDto> findById(String id);

    CommentDto create(String comment, String bookId);

    void update(String id, String comment);

    void deleteById(String id);

}
