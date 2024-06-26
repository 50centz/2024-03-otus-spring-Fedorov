package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentConverter {

    private final BookConverter bookConverter;

    public String commentToString(CommentDto commentDto) {
        return "Id: %s, comment: %s, book: %s".formatted(
                commentDto.getId(),
                commentDto.getComment(),
                bookConverter.bookToString(commentDto.getBookDto()));
    }

}
