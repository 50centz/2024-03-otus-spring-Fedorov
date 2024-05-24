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
    public List<Comment> findAllCommentByBookId(long id) {
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
        return save(0, comment, bookId);
    }

    @Transactional
    @Override
    public void updateCommentById(long id, String comment) {
        Optional<Comment> oldComment = commentRepository.findById(id);

        if (oldComment.isPresent()) {
            Comment newComment = oldComment.get();
            newComment.setComment(comment);
            commentRepository.save(newComment);
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteCommentBuId(id);
    }


    @Transactional
    @Override
    public void deleteAllCommentByBookId(long id) {
        commentRepository.deleteAllCommentByBookId(id);
    }


    private Comment save(long id, String comment, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var commentt = new Comment(id, comment, book);
        return commentRepository.save(commentt);
    }
}
