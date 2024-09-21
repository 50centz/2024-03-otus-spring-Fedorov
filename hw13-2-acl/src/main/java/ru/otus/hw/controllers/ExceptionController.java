package ru.otus.hw.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.hw.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleEntityNotFoundEx(NotFoundException exception) {
        var errorMessage = exception.getMessage();
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", errorMessage);
        mav.setViewName("errors/exception");
        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationExceptions(
            MethodArgumentNotValidException exception) {

        List<String> errorMessages = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(errorMessage);
        });

        ModelAndView mav = new ModelAndView();
        var errorMessage = errorMessages.get(0);
        mav.addObject("errorMessage", errorMessage);
        mav.setViewName("errors/exception");
        return mav;

    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeException(RuntimeException exception) {
        ModelAndView mav = new ModelAndView();
        var errorMessage = exception.getMessage();
        mav.addObject("errorMessage", errorMessage);
        mav.setViewName("errors/exception");
        return mav;

    }
}
