package ru.otus.hw.repositories;


import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw.models.Comment;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {


    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteAllByBookId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("book.$id").is(new ObjectId(id)));
        mongoTemplate.findAllAndRemove(query, Comment.class);
    }

    @Override
    public List<Comment> findAllByBookId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("book.$id").is(new ObjectId(id)));
        return mongoTemplate.find(query, Comment.class);
    }
}
