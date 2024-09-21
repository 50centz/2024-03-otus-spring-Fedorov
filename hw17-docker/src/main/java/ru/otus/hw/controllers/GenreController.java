package ru.otus.hw.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre-create")
    public String create() {
        return "create-genre";
    }

    @PostMapping("/genre-create")
    public String save(@RequestParam("name") String name) {
        genreService.create(name);
        return "redirect:/";
    }
}
