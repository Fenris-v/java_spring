package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.exceptions.EmptySearchException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(@NotNull EmptySearchException exception,
                                             @NotNull RedirectAttributes redirectAttributes,
                                             @NotNull HttpServletRequest request) {
        Logger.getLogger(this.getClass().getSimpleName()).warning(exception.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", exception);
        return "redirect:" + request.getHeader(HttpHeaders.REFERER);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Map<String, ?> handleValidationExceptions(@NotNull BindException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("result", false);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
