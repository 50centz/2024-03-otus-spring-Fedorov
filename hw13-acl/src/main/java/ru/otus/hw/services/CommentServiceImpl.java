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

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByBookId(Long id) {
        return commentRepository.findAllByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByBookId(long id) {

        return commentRepository.findAllByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDto> findById(long id) {

        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            return Optional.of(commentMapper.toDto(comment.get()));
        }

        throw new NotFoundException("Comment with id %d not found".formatted(id));
    }

    @Transactional
    @Override
    public Comment create(String comment, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        return commentRepository.save(new Comment(0, comment, book));
    }

    @Transactional
    @Override
    public void update(long id, String comment) {
        Comment commentInDb = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment " +
                "with id %d not found".formatted(id)));

        commentInDb.setComment(comment);

        commentRepository.save(commentInDb);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }


}
