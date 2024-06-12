package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private GenreDto genreDto;

    private List<AuthorDto> authorsDto;
}
