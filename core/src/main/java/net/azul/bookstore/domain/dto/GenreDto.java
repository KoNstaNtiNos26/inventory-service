package net.azul.bookstore.domain.dto;

import net.azul.bookstore.domain.entity.Genre;

public class GenreDto {
    private Long id;
    private String title;

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

    public static GenreDto fromGenre(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setTitle(genre.getTitle());
        return genreDto;
    }
}
