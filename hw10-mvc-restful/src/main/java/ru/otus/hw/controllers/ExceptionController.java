package ru.otus.hw.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.dto.ErrorDto;
import ru.otus.hw.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto notFoundException(NotFoundException exception) {
        log.error(exception.getMessage());
        return new ErrorDto(exception.getMessage());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationExceptions(
            MethodArgumentNotValidException exception) {

        List<String> errorMessages = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });

        log.error(errorMessages.get(0));
        return new ErrorDto(errorMessages.get(0));

        //https://www.baeldung.com/spring-boot-bean-validation
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto runtimeException(RuntimeException exception) {
        log.error(exception.getMessage());
        return new ErrorDto(exception.getMessage());
    }
}
