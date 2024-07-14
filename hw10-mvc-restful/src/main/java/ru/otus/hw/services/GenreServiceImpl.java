package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.dto.mapper.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;


    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).toList();
    }

    @Transactional
    @Override
    public GenreDto create(String name) {
        return genreMapper.toDto(genreRepository.save(new Genre(null, name)));
    }

}
