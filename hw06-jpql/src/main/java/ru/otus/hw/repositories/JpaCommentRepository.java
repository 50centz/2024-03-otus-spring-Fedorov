package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {


    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Comment> findAllCommentByBookId(long id) {

        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c where c.book.id = :id",
                Comment.class);
        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }


    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }

        return entityManager.merge(comment);
    }



    @Override
    public void deleteCommentBuId(long id) {
        Comment comment = entityManager.find(Comment.class, id);

        entityManager.remove(comment);
    }


    @Override
    public void deleteAllCommentByBookId(long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.book.id = :id");

        query.setParameter("id", id).executeUpdate();
    }
}
