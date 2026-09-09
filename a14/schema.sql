-- schema.sql — CX651 Assignment 14
-- CREATE TABLE statements for the books_server PostgreSQL database.
-- Load with: psql books_server < schema.sql

DROP TABLE IF EXISTS books   CASCADE;
DROP TABLE IF EXISTS authors CASCADE;

CREATE TABLE authors (
    author_id   SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    country     VARCHAR(50)  NOT NULL
);

CREATE TABLE books (
    book_id     SERIAL PRIMARY KEY,
    author_id   INTEGER REFERENCES authors(author_id),
    title       VARCHAR(200) NOT NULL,
    genre       VARCHAR(50)  NOT NULL,
    pub_year    INTEGER,
    price       NUMERIC(6,2) NOT NULL
);
