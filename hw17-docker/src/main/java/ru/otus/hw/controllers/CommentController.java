package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/comments")
    public String comments(@RequestParam("id") long id, Model model) {
        String idComment = String.valueOf(id);
        List<CommentDto> commentDtoList = commentService.findAllByBookId(idComment);
        BookDto bookDto = bookService.findById(idComment);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("bookDto", bookDto);
        return "comments";
    }

    @GetMapping("/comment/delete")
    public String deleteById(@RequestParam("id") long id) {
        String idDel = Long.toString(id);
        String idBook = "";
        Optional<CommentDto> commentDto = commentService.findById(idDel);
        if (commentDto.isPresent()) {
            idBook = commentDto.get().getBookDto().getId();
        }
        
        commentService.deleteById(idDel);

        return "redirect:/comments?id=" + idBook;
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
