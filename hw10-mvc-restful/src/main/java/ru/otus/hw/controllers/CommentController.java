package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/comments/{id}")
    public List<CommentDto> comments(@PathVariable("id") String id) {
        return commentService.findAllByBookId(id);
    }

    @DeleteMapping("/api/comments/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        commentService.deleteById(id);
    }


    @PostMapping("/api/comments/create")
    public CommentDto save(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        return commentService.create(commentCreateDto.getComment(), commentCreateDto.getBookId());
    }


}
