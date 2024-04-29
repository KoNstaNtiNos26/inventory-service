package net.azul.bookstore.repository;

import net.azul.bookstore.domain.entity.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<Book> findAllByGenreId(Long genreId, Pageable pageable);

    Page<Book> findAllByTitle(String title, Pageable pageable);
}
