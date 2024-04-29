package net.azul.bookstore.service;

import net.azul.bookstore.domain.dto.AuthorDto;
import net.azul.bookstore.domain.dto.BookDto;
import net.azul.bookstore.domain.dto.GenreDto;
import net.azul.bookstore.domain.entity.Author;
import net.azul.bookstore.domain.entity.Book;
import net.azul.bookstore.domain.entity.Genre;
import net.azul.bookstore.repository.JpaBookRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);

    private final AuthorService authorService;
    private final GenreService genreService;
    private final JpaBookRepository bookRepository;

    public BookService(AuthorService authorService, GenreService genreService, JpaBookRepository bookRepository) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public boolean addBook(BookDto bookDto) {
        if(Objects.isNull(bookDto.getId())) {
            Book book = new Book();
            AuthorDto authorDto = bookDto.getAuthor();
            GenreDto genreDto = bookDto.getGenre();
            if(Objects.nonNull(authorDto)) {
                Author author = null;
                if (Objects.isNull(authorDto.getId())) {
                    author = new Author();
                    author.setFirstName(authorDto.getFirstName());
                    author.setLastName(authorDto.getLastName());
                    author.setCreatedAt(LocalDateTime.now());
                    author.setUpdatedAt(LocalDateTime.now());
                }
                else {
                    author = authorService.findById(authorDto.getId())
                            .orElse(null);
                }
                book.setAuthor(author);
            }
            if(Objects.nonNull(genreDto)) {
                Genre genre = null;
                if (Objects.isNull(genreDto.getId())) {
                    genre = new Genre();
                    genre.setTitle(genreDto.getTitle());
                    genre.setCreatedAt(LocalDateTime.now());
                    genre.setUpdatedAt(LocalDateTime.now());
                }
                else {
                    genre = genreService.findById(genreDto.getId())
                            .orElse(null);
                }
                book.setGenre(genre);
            }
            book.setTitle(bookDto.getTitle());
            book.setPrice(bookDto.getPrice());
            bookRepository.save(book);
            logger.info("Book [{}] has been added", bookDto.getId());
            return true;
        }
        logger.info("unable to add new book [{}]", bookDto.getTitle());
        return false;
    }

    @Transactional
    public boolean updateBook(BookDto bookDto) {
        if (Objects.nonNull(bookDto.getId())) {
            Optional<Book> optBook = bookRepository.findById(bookDto.getId());
            if (optBook.isPresent()) {
                Book book = optBook.get();
                book.setTitle(bookDto.getTitle());
                book.setPrice(bookDto.getPrice());
                book.setUpdatedAt(LocalDateTime.now());
                bookRepository.save(book);
                logger.info("Book [{}] has been updated", bookDto.getId());
                return true;
            }
        }
        logger.info("unable to update book [{}]", bookDto.getId());
        return false;
    }

    @Transactional
    public boolean deleteBook(BookDto bookDto) {
        if (Objects.nonNull(bookDto.getId())) {
            bookRepository.deleteById(bookDto.getId());
            logger.info("Book [{}] has been deleted", bookDto.getId());
            return true;
        }
        logger.info("unable to delete book [{}]", bookDto.getId());
        return false;
    }

    public Page<BookDto> findBooksByTitle(String title, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        if (StringUtils.isNotBlank(title)) {
            return bookRepository.findAllByTitle(title, pageRequest)
                    .map(BookDto::fromBook);
        }
        return Page.empty();
    }

    public Page<BookDto> findBooksByAuthor(AuthorDto authorDto, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        if (Objects.nonNull(authorDto.getId())) {
            return bookRepository.findAllByAuthorId(authorDto.getId(), pageRequest)
                    .map(BookDto::fromBook);
        }

        Optional<Long> authorId = Optional.empty();
        if (StringUtils.isNotBlank(authorDto.getFirstName()) && StringUtils.isNotBlank(authorDto.getLastName())) {
            authorId = authorService.findByFirstNameAndLastName(authorDto.getFirstName(), authorDto.getLastName())
                    .stream()
                    .findFirst()
                    .map(Author::getId);
        }
        if (authorId.isEmpty() && StringUtils.isNotBlank(authorDto.getLastName())) {
            authorId = authorService.findByLastName(authorDto.getLastName())
                    .stream()
                    .findFirst()
                    .map(Author::getId);
        }
        return authorId.map(aLong -> bookRepository.findAllByAuthorId(aLong, pageRequest)
                .map(BookDto::fromBook)).orElseGet(Page::empty);
    }

    public Page<BookDto> findBooksByGenre(GenreDto genreDto, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        if (Objects.nonNull(genreDto.getId())) {
            return bookRepository.findAllByGenreId(genreDto.getId(), pageRequest)
                    .map(BookDto::fromBook);
        }
        if (StringUtils.isNotBlank(genreDto.getTitle())) {
            Optional<Long> optionalGenreId = genreService.findGenreByTitle(genreDto.getTitle())
                    .map(Genre::getId);
            if (optionalGenreId.isPresent()) {
                return bookRepository.findAllByGenreId(optionalGenreId.get(), pageRequest)
                        .map(BookDto::fromBook);
            }
        }
        return Page.empty();
    }
}
