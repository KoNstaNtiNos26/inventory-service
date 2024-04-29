package net.azul.bookstore.service;

import net.azul.bookstore.domain.dto.AuthorDto;
import net.azul.bookstore.domain.dto.BookDto;
import net.azul.bookstore.domain.dto.GenreDto;
import net.azul.bookstore.domain.entity.Book;
import net.azul.bookstore.repository.JpaBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @Mock
    private JpaBookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    private final String TITLE = "Title";
    private final Long ID = 1L;


    @Test
    void addBookTest() {
        //given
        BookDto bookDto = getBookDto(getAuthorDto(), getGenreDto());
        bookDto.setId(null);
        //when
        bookService.addBook(bookDto);
        //then
        verify(authorService).findById(ID);
        verify(genreService).findById(ID);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBookTest() {
        //given
        BookDto bookDto = getBookDto(getAuthorDto(), getGenreDto());
        Book book = mock(Book.class);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        //when
        bookService.updateBook(bookDto);
        //then
        verify(bookRepository).save(book);
    }

    @Test
    void deleteBookTest() {
        //given
        BookDto bookDto = getBookDto(getAuthorDto(), getGenreDto());
        //when
        bookService.deleteBook(bookDto);
        //then
        verify(bookRepository).deleteById(anyLong());
    }

    @Test
    void findBookByTitleTest() {
        //given
        when(bookRepository.findAllByTitle(anyString(), any(Pageable.class))).thenReturn(Page.empty());
        //when
        bookService.findBooksByTitle(TITLE, 0,1);
        //then
        verify(bookRepository).findAllByTitle(anyString(), any(Pageable.class));
    }

    @Test
    void findBookByAuthorTest() {
        //given
        //when
        //then
    }

    @Test
    void findBookByGenreTest() {
        //given
        //when
        //then
    }

    private BookDto getBookDto(AuthorDto authorDto, GenreDto genreDto) {
        BookDto bookDto = new BookDto();
        bookDto.setId(ID);
        bookDto.setTitle(TITLE);
        bookDto.setPrice(100);
        bookDto.setAuthor(authorDto);
        bookDto.setGenre(genreDto);
        return bookDto;
    }

    public AuthorDto getAuthorDto() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(ID);
        authorDto.setFirstName("Bob");
        authorDto.setLastName("Doe");
        return authorDto;
    }

    public GenreDto getGenreDto() {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(ID);
        genreDto.setTitle("Horror");
        return genreDto;
    }
}
