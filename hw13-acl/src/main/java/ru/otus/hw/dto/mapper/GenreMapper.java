package ru.otus.hw.dto.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {

        return new GenreDto(genre.getId(), genre.getName());
    }
}
