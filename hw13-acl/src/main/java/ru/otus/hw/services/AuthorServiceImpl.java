package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.mapper.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {

        return authorRepository.findAll().stream().map(authorMapper::toDto).toList();
    }

    @Transactional
    @Override
    public Author create(String fullName) {

        return authorRepository.save(new Author(0L, fullName));
    }

}
