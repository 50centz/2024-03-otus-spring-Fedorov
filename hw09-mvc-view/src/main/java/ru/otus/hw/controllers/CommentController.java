package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/comments/{id}")
    public String comments(@PathVariable("id") String id, Model model) {
        List<CommentDto> commentDtoList = commentService.findAllByBookId(id);
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("bookDto", bookDto);
        return "comments";
    }

    @PostMapping("/comment/delete/{id}")
    public String deleteById(@PathVariable("id") String id) {
        String idBook = "";
        Optional<CommentDto> commentDto = commentService.findById(id);
        if (commentDto.isPresent()) {
            idBook = commentDto.get().getBookDto().getId();
        }
        
        commentService.deleteById(id);

        return "redirect:/comments/" + idBook;
    }


    @GetMapping("/comment-create")
    public String commentSave(Model model) {
        List<BookDto> bookDtoList = bookService.findAll();
        model.addAttribute("bookDtoList", bookDtoList);
        return "create-comment";
    }

    @PostMapping("/comment-create")
    public String save(@RequestParam String comment, @RequestParam String bookId) {
        commentService.create(comment, bookId);
        return "redirect:/";
    }


}
