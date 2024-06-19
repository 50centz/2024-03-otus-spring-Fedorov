package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.dto.mapper.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).toList();
    }

    @Transactional
    @Override
    public GenreDto create(String name) {
        return genreMapper.toDto(genreRepository.save(new Genre(getId(), name)));
    }

    private String getId() {
        Optional<Genre> genre = genreRepository.findAll().stream().reduce((b1, b2) -> b2);

        if (genre.isPresent()) {
            String number = genre.get().getId();
            int i = Integer.parseInt(number);
            i++;
            return Integer.toString(i);
        }

        return "1";
    }
}
