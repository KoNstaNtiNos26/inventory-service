package net.azul.bookstore.service;

import net.azul.bookstore.domain.entity.Genre;
import net.azul.bookstore.repository.JpaGenreRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService {

    private final JpaGenreRepository genreRepository;

    private GenreService(JpaGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> findGenreByTitle(String title) {
        return genreRepository.findByTitle(title);
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }
}
