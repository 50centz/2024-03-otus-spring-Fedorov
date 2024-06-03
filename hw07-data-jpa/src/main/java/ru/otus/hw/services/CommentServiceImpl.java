package ru.otus.hw.services;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(long id) {
        return commentRepository.findAllCommentByBookId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    @Override
    public Comment create(String comment, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        return commentRepository.save(new Comment(0, comment, book));
    }

    @Transactional
    @Override
    public void update(long id, String comment) {
        Comment commentInDb = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment " +
                "with id %d not found".formatted(id)));

        commentInDb.setComment(comment);

        commentRepository.save(commentInDb);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void deleteByBookId(long id) {
        commentRepository.deleteAllCommentByBookId(id);
    }



}
