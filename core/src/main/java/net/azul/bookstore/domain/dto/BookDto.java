package net.azul.bookstore.domain.dto;

import jakarta.persistence.ManyToOne;
import net.azul.bookstore.domain.entity.Author;
import net.azul.bookstore.domain.entity.Book;
import net.azul.bookstore.domain.entity.Genre;

public class BookDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public void setGenre(GenreDto genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static BookDto fromBook(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(bookDto.getPrice());
        bookDto.setAuthor(AuthorDto.fromAuthor(book.getAuthor()));
        bookDto.setGenre(GenreDto.fromGenre(book.getGenre()));
        return bookDto;
    }
}
