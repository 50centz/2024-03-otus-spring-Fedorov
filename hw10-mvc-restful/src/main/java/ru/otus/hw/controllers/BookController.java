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
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.exceptions.InternalException;
import ru.otus.hw.services.BookService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public ResponseEntity<List<BookDto>> generalPage() {
        List<BookDto> bookDtoList = bookService.findAll();
        return ResponseEntity.ok(bookDtoList);
    }

    @DeleteMapping("/api/books/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/books/create")
    public ResponseEntity<BookDto> save(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return ResponseEntity.ok(bookService.create(bookCreateDto));
    }


    @PostMapping("/api/books/edit")
    public ResponseEntity<BookDto> update(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return ResponseEntity.ok(bookService.update(bookUpdateDto));
    }

    @GetMapping("/api/books/exception/{exceptionId}")
    public void getSpecificException(@PathVariable("exceptionId") String exception) {
        if ("404".equals(exception)) {
            throw new NotFoundException("404 Not Found");
        }
        if ("500".equals(exception)) {
            throw new InternalException("500 Internal Server Error");
        }

        //https://www.baeldung.com/spring-mvc-test-exceptions
    }
}
