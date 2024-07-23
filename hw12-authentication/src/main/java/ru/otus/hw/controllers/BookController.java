package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
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


    @PostMapping("/book/delete/{id}")
    public String deleteById(@PathVariable("id") String id) {
        bookService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/book-create")
    public String create(Model model) {
        BookCreateDto bookCreateDto = new BookCreateDto();
        List<GenreDto> genreDtoList = genreService.findAll();
        List<AuthorDto> authorDtoList = authorService.findAll();
        model.addAttribute("genreDtoList", genreDtoList);
        model.addAttribute("authorDtoList", authorDtoList);
        model.addAttribute("bookCreateDto", bookCreateDto);
        return "create-book";
    }

    @PostMapping("/book-create")
    public String save(@Valid BookCreateDto bookCreateDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<GenreDto> genreDtoList = genreService.findAll();
            List<AuthorDto> authorDtoList = authorService.findAll();
            model.addAttribute("genreDtoList", genreDtoList);
            model.addAttribute("authorDtoList", authorDtoList);
            return "create-book";
        }

        bookService.create(bookCreateDto);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        BookDto bookDto = bookService.findById(id);
        List<GenreDto> genreDtoList = genreService.findAll();
        List<AuthorDto> authorDtoList = authorService.findAll();

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("genreDtoList", genreDtoList);
        model.addAttribute("authorDtoList", authorDtoList);
        model.addAttribute("bookUpdateDto", bookUpdateDto);

        return "edit";
    }

    @PostMapping("/edit")
    public String update(@Valid BookUpdateDto bookUpdateDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            BookDto bookDto = bookService.findById(bookUpdateDto.getBookId());
            List<GenreDto> genreDtoList = genreService.findAll();
            List<AuthorDto> authorDtoList = authorService.findAll();

            model.addAttribute("bookDto", bookDto);
            model.addAttribute("genreDtoList", genreDtoList);
            model.addAttribute("authorDtoList", authorDtoList);

            return "edit";
        }

        bookService.update(bookUpdateDto);
        return "redirect:/";
    }

}
