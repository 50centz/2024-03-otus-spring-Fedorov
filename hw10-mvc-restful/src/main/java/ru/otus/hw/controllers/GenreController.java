package ru.otus.hw.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.GenreCreateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/api/genres/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public GenreDto save(@RequestBody GenreCreateDto genreCreateDto) {
        return genreService.create(genreCreateDto.getName());
    }

}
