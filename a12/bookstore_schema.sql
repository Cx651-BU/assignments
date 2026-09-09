-- bookstore_schema.sql
-- CX651 Assignment 12: SQL Query Dissection
-- Schema for the bookstore PostgreSQL database.
-- Load with: psql bookstore < bookstore_schema.sql

DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders      CASCADE;
DROP TABLE IF EXISTS customers   CASCADE;
DROP TABLE IF EXISTS books       CASCADE;
DROP TABLE IF EXISTS authors     CASCADE;

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

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) UNIQUE NOT NULL
);

CREATE TABLE orders (
    order_id    SERIAL PRIMARY KEY,
    customer_id INTEGER REFERENCES customers(customer_id),
    order_date  DATE NOT NULL
);

CREATE TABLE order_items (
    item_id     SERIAL PRIMARY KEY,
    order_id    INTEGER REFERENCES orders(order_id),
    book_id     INTEGER REFERENCES books(book_id),
    quantity    INTEGER NOT NULL DEFAULT 1,
    unit_price  NUMERIC(6,2) NOT NULL
);
