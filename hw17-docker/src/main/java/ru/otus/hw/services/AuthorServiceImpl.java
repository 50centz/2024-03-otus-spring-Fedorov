package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.mapper.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).toList();
    }

    @Transactional
    @Override
    public AuthorDto create(String fullName) {
        return authorMapper.toDto(authorRepository.save(new Author(getId(), fullName)));
    }

    private String getId() {
        Optional<Author> author = authorRepository.findAll().stream().reduce((b1, b2) -> b2);

        if (author.isPresent()) {
            String number = author.get().getId();
            int i = Integer.parseInt(number);
            i++;
            return Integer.toString(i);
        }

        return "1";
    }

}
