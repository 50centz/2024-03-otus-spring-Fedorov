package ru.otus.hw.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookDto toDto(Book book) {

        List<AuthorDto> authorDtoList = authorMapper.toDtoList(book.getAuthors());

        return new BookDto(book.getId().toString(), book.getTitle(), genreMapper.toDto(book.getGenre()), authorDtoList);
    }

}
