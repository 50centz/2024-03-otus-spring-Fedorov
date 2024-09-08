package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.services.GenreService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final CommentServiceImpl commentService;

    private final GenreService genreService;

    private final AuthorService authorService;


    @GetMapping("/")
    public String generalPage(Model model) {
        List<BookDto> bookDtoList = bookService.findAll();
        model.addAttribute("bookDtoList", bookDtoList);
        return "index";
    }


    @GetMapping("/book/delete")
    public String deleteById(@RequestParam("id") long id) {
        bookService.deleteById(Long.toString(id));
        return "redirect:/";
    }


    @GetMapping("/book-create")
    public String create(Model model) {
        List<GenreDto> genreDtoList = genreService.findAll();
        List<AuthorDto> authorDtoList = authorService.findAll();
        model.addAttribute("genreDtoList", genreDtoList);
        model.addAttribute("authorDtoList", authorDtoList);
        return "create-book";
    }

    @PostMapping("/book-create")
    public String save(@RequestParam String title, @RequestParam String genre,
                           @RequestParam String author) {
        bookService.create(title, genre, author);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto bookDto = bookService.findById(Long.toString(id));
        List<GenreDto> genreDtoList = genreService.findAll();
        List<AuthorDto> authorDtoList = authorService.findAll();

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("genreDtoList", genreDtoList);
        model.addAttribute("authorDtoList", authorDtoList);

        return "edit";
    }

    @PostMapping("/edit")
    public String update(@RequestParam String bookId, @RequestParam String title,
                         @RequestParam String genre, @RequestParam String author) {
        bookService.update(bookId, title, genre, author);
        return "redirect:/";
    }

}
