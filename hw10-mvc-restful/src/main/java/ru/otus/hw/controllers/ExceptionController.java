package ru.otus.hw.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.exceptions.InternalException;
import ru.otus.hw.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {

        List<String> errorMessages = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorMessages.get(0));

        //https://www.baeldung.com/spring-boot-bean-validation
    }


    @ExceptionHandler(InternalException.class)
    public ResponseEntity<String> internalException(InternalException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
