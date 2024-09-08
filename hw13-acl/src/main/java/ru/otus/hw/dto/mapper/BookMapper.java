package ru.otus.hw.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;


@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookDto toDto(Book book) {

        return new BookDto(book.getId(), book.getTitle(), genreMapper.toDto(book.getGenre()),
                authorMapper.toDto(book.getAuthor()));
    }

    public Book toModel(BookUpdateDto bookUpdateDto) {

        Long authorId = bookUpdateDto.getAuthor();
        Long genreId = bookUpdateDto.getGenre();

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(genreId)));

        return new Book(bookUpdateDto.getBookId(), bookUpdateDto.getTitle(), author, genre);
    }

}
