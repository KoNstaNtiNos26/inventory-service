package net.azul.bookstore.service;

import net.azul.bookstore.domain.dto.AuthorDto;
import net.azul.bookstore.domain.entity.Author;
import net.azul.bookstore.repository.JpaAuthorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final JpaAuthorRepository authorRepository;

    public AuthorService(JpaAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public List<Author> findByLastName(String lastName) {
        return authorRepository.findAllByLastName(lastName);
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
}
