package net.azul.bookstore.repository;

import jakarta.persistence.QueryHint;
import net.azul.bookstore.domain.entity.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.Optional;

public interface JpaGenreRepository extends JpaRepository<Genre, Long> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Genre> findByTitle(String title);

    @Query("select g FROM Genre g " +
           "LEFT JOIN FETCH g.books b " +
           "WHERE g.title = :title")
    List<Genre> findByTitleWithBooks(String title);

    @Query("select g FROM Genre g " +
           "LEFT JOIN FETCH g.books b " +
           "WHERE g.id = :id")
    Optional<Genre> findByIdWithBooks(Long id);
}
