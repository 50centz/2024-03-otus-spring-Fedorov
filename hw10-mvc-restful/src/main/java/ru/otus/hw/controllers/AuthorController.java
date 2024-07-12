package ru.otus.hw.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.AuthorCreateDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/api/authors/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public AuthorDto save(@RequestBody AuthorCreateDto authorCreateDto) {
        return authorService.create(authorCreateDto.getFullName());
    }
}
