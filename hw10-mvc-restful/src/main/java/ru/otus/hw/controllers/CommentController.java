package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<CommentDto>> comments(@PathVariable("id") String id) {
        return ResponseEntity.ok(commentService.findAllByBookId(id));
    }

    @DeleteMapping("/api/comments/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/api/comments/create")
    public ResponseEntity<CommentDto> save(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        return ResponseEntity.ok(commentService.create(commentCreateDto.getComment(), commentCreateDto.getBookId()));
    }


}
