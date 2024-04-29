package net.azul.bookstore.controller;

import net.azul.bookstore.domain.dto.AuthorDto;
import net.azul.bookstore.domain.dto.BookDto;
import net.azul.bookstore.domain.dto.GenreDto;
import net.azul.bookstore.service.BookService;
import net.azul.bookstore.utils.PageUtils;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final BookService bookService;

    private SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/author")
    public Page<BookDto> searchByAuthor(@RequestBody AuthorDto authorDto, @RequestParam(name = "page") int page) {
        return bookService.findBooksByAuthor(authorDto, page, PageUtils.PAGE_SIZE);
    }

    @GetMapping(value = "/genre")
    public Page<BookDto> searchByGenre(@RequestBody GenreDto genreDto, @RequestParam(name = "page") int page) {
        return bookService.findBooksByGenre(genreDto, page, PageUtils.PAGE_SIZE);
    }

    @GetMapping(value = "/title")
    public Page<BookDto> searchByTitle(@RequestBody String title, @RequestParam(name = "page") int page) {
        return bookService.findBooksByTitle(title, page, PageUtils.PAGE_SIZE);
    }
}
