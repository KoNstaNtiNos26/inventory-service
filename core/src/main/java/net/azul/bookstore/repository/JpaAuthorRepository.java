package net.azul.bookstore.repository;

import jakarta.persistence.QueryHint;
import net.azul.bookstore.domain.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.Optional;

public interface JpaAuthorRepository extends JpaRepository<Author, Long> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<Author> findAllByFirstNameAndLastName(String firstName, String lastName);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<Author> findAllByLastName(String lastName);

    @Query("select a FROM Author a " +
           "LEFT JOIN FETCH a.books b " +
           "WHERE a.firstName = :firstName and a.lastName = :lastName")
    List<Author> findByFirstNameAndLastNameWithBooks(String firstName, String lastName);

    @Query("select a FROM Author a " +
           "LEFT JOIN FETCH a.books b " +
           "WHERE a.lastName = :lastName")
    List<Author> findByLastNameWithBooks(String lastName);

    @Query("select a FROM Author a " +
           "LEFT JOIN FETCH a.books b " +
           "WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(Long id);
}
