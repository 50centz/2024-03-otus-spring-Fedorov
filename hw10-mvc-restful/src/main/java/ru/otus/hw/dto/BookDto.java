package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class BookDto {

    private String id;

    private String title;

    private GenreDto genreDto;

    private List<AuthorDto> authorsDto;
}
