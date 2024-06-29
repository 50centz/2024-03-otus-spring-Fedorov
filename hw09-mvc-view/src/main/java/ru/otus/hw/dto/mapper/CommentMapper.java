package ru.otus.hw.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final BookMapper bookMapper;

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId().toString(), comment.getComment(), bookMapper.toDto(comment.getBook()));
    }

}
