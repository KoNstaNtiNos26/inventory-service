CREATE TABLE IF NOT EXISTS inv.author
(
    id         BIGSERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS inv.genre
(
    id         BIGSERIAL PRIMARY KEY,
    title      TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS inv.book
(
    id         BIGSERIAL PRIMARY KEY,
    title      TEXT NOT NULL,
    author_id  BIGINT REFERENCES inv.author(id)
        ON DELETE CASCADE,
    genre_id   BIGINT REFERENCES inv.genre(id),
    price      NUMERIC,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS inv.author_to_genre
(
    id        BIGSERIAL PRIMARY KEY,
    author_id BIGINT REFERENCES inv.author(id)
        ON DELETE CASCADE,
    genre_id  BIGINT REFERENCES inv.genre(id)
);

CREATE INDEX IF NOT EXISTS book_title_idx ON inv.book(title);
CREATE INDEX IF NOT EXISTS book_author_idx ON inv.book(author_id);
CREATE INDEX IF NOT EXISTS book_genre_idx ON inv.book(genre_id);