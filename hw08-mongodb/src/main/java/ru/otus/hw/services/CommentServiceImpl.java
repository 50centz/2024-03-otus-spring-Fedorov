package ru.otus.hw.services;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.mapper.CommentMapper;
import ru.otus.hw.exceptions.EntityNotFoundException;
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
    public List<CommentDto> findAllByBookId(String id) {
        return commentRepository.findAllByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDto> findById(String id) {
        return Optional.of(commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)))));

    }

    @Transactional
    @Override
    public CommentDto create(String comment, String bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        return commentMapper.toDto(commentRepository.save(new Comment(getId(), comment, book)));
    }

    @Transactional
    @Override
    public void update(String id, String comment) {
        Comment commentInDb = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment " +
                "with id %s not found".formatted(id)));

        commentInDb.setComment(comment);

        commentRepository.save(commentInDb);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    private String getId() {
        Optional<Comment> comment = commentRepository.findAll().stream().reduce((b1, b2) -> b2);

        if (comment.isPresent()) {
            String number = comment.get().getId();
            int i = Integer.parseInt(number);
            i++;
            return Integer.toString(i);
        }

        return "1";
    }


}
