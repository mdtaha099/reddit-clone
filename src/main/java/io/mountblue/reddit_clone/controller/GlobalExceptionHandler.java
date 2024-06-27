package io.mountblue.reddit_clone.controller;

import io.mountblue.reddit_clone.exception.UserAlreadyExistsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String handleException(UserAlreadyExistsException exc, Model model) {
        model.addAttribute("message", exc.getMessage());
        return "error/signup-error";
    }

    @ExceptionHandler
    public String handleException(Exception exc, Model model) {
        model.addAttribute("message", exc.getMessage());
        return "error/exception";
    }
}
