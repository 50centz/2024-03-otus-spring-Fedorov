package ru.otus.hw.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.AuthorService;


@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/author-create")
    public String create() {
        return "create-author";
    }

    @PostMapping("/author-create")
    public String save(@RequestParam String fullName) {
        authorService.create(fullName);
        return "redirect:/";
    }
}
