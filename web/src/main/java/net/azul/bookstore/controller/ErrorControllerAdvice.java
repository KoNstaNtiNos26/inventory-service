package net.azul.bookstore.controller;

import net.azul.bookstore.service.BookService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestController
@RestControllerAdvice
public class ErrorControllerAdvice implements ErrorController {

    private static final Logger logger = LogManager.getLogger(BookService.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        logger.error("Unexpected exception", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(rootCause.getMessage());
    }
}
