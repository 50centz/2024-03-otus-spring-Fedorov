package ru.otus.hw.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    private final BookRepository bookRepository;

    public BookDto toDto(Book book) {

        List<AuthorDto> authorDtoList = authorMapper.toDtoList(book.getAuthors());

        return new BookDto(book.getId().toString(), book.getTitle(), genreMapper.toDto(book.getGenre()), authorDtoList);
    }

    public Book toModel(BookUpdateDto bookUpdateDto, Genre genre, List<Author> authors) {

        bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Book with id %s not found".formatted(bookUpdateDto.getId())));

        return new Book(bookUpdateDto.getId(), bookUpdateDto.getTitle(), genre, authors);
    }

}
