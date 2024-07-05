package ru.otus.hw.services;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.mapper.CommentMapper;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByBookId(String id) {

        bookService.findById(id);

        return commentRepository.findAllByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDto> findById(String id) {
        return Optional.of(commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id %s not found".formatted(id)))));

    }

    @Transactional
    @Override
    public CommentDto create(String comment, String bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %s not found".formatted(bookId)));

        return commentMapper.toDto(commentRepository.save(new Comment(null, comment, book)));
    }

    @Transactional
    @Override
    public void update(String id, String comment) {
        Comment commentInDb = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment " +
                "with id %s not found".formatted(id)));

        commentInDb.setComment(comment);

        commentRepository.save(commentInDb);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id %s not found".formatted(id))));
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteAllByBookId(String id) {
        commentRepository.deleteAllByBookId(id);
    }

}
