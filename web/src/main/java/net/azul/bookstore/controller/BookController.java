package net.azul.bookstore.controller;

import net.azul.bookstore.domain.dto.AuthorDto;
import net.azul.bookstore.domain.dto.BookDto;
import net.azul.bookstore.service.BookService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    private BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/add")
    public boolean addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @PutMapping (value = "/update")
    public boolean updateBook(@RequestBody BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteBook(@RequestBody BookDto bookDto) {
        return bookService.deleteBook(bookDto);
    }
}
