-- bookstore_data.sql
-- CX651 Assignment 12: SQL Query Dissection
-- Seed data for the bookstore PostgreSQL database.
-- 8 authors, 19 books, 8 customers, 15 orders, 23 order items.
-- Load AFTER bookstore_schema.sql.

-- ─── Authors (8) ────────────────────────────────────────────────────────────
-- author_id 7 (Chen) and 8 (Okafor) have no books → appear only in LEFT JOIN

INSERT INTO authors (name, country) VALUES
    ('George Orwell',       'UK'),         -- 1
    ('Kazuo Ishiguro',      'UK'),         -- 2
    ('Virginia Woolf',      'UK'),         -- 3
    ('Gabriel García Márquez', 'Colombia'),-- 4
    ('Toni Morrison',       'USA'),        -- 5
    ('Haruki Murakami',     'Japan'),      -- 6
    ('Lin Chen',            'China'),      -- 7  (no books — LEFT JOIN test)
    ('Amara Okafor',        'Nigeria');    -- 8  (no books — LEFT JOIN test)

-- ─── Books (19) ─────────────────────────────────────────────────────────────
-- 6 distinct genres: Fiction, Dystopian, Classic, Historical Fiction,
--                    Literary Fiction, Magical Realism
-- BETWEEN test: two books priced exactly $12.99 and $13.99

INSERT INTO books (author_id, title, genre, pub_year, price) VALUES
    -- George Orwell (UK)
    (1, 'Nineteen Eighty-Four',   'Dystopian',        1949,  9.99),
    (1, 'Animal Farm',            'Dystopian',        1945,  7.99),
    (1, 'Homage to Catalonia',    'Non-Fiction',      1938, 12.99),  -- BETWEEN boundary low

    -- Kazuo Ishiguro (UK)
    (2, 'The Remains of the Day', 'Literary Fiction', 1989, 13.99),  -- BETWEEN boundary high
    (2, 'Never Let Me Go',        'Literary Fiction', 2005, 11.99),
    (2, 'Klara and the Sun',      'Literary Fiction', 2021, 14.99),

    -- Virginia Woolf (UK)
    (3, 'Mrs Dalloway',           'Classic',          1925,  8.49),
    (3, 'To the Lighthouse',      'Classic',          1927,  8.99),
    (3, 'Orlando',                'Classic',          1928,  9.49),

    -- Gabriel García Márquez (Colombia)
    (4, 'One Hundred Years of Solitude', 'Magical Realism', 1967, 10.99),
    (4, 'Love in the Time of Cholera',   'Magical Realism', 1985, 11.49),
    (4, 'Chronicle of a Death Foretold', 'Magical Realism', 1981,  8.99),

    -- Toni Morrison (USA)
    (5, 'Beloved',                'Historical Fiction', 1987, 10.49),
    (5, 'Song of Solomon',        'Fiction',            1977,  9.99),
    (5, 'The Bluest Eye',         'Fiction',            1970,  8.99),

    -- Haruki Murakami (Japan)
    (6, 'Norwegian Wood',         'Fiction',            1987, 10.99),
    (6, 'Kafka on the Shore',     'Fiction',            2002, 12.49),
    (6, 'The Wind-Up Bird Chronicle', 'Fiction',        1994, 13.49),
    (6, '1Q84',                   'Fiction',            2009, 15.99);

-- ─── Customers (8) ──────────────────────────────────────────────────────────

INSERT INTO customers (name, email) VALUES
    ('Alice Brennan',   'alice@example.com'),    -- 1
    ('Bob Tanaka',      'bob@example.com'),       -- 2
    ('Carol Liu',       'carol@example.com'),     -- 3
    ('David Osei',      'david@example.com'),     -- 4
    ('Elena Petrov',    'elena@example.com'),     -- 5
    ('Frank Novak',     'frank@example.com'),     -- 6
    ('Grace Kim',       'grace@example.com'),     -- 7
    ('Henry Walsh',     'henry@example.com');     -- 8

-- ─── Orders (15) ────────────────────────────────────────────────────────────

INSERT INTO orders (customer_id, order_date) VALUES
    (1, '2024-01-10'),   -- order 1
    (1, '2024-03-05'),   -- order 2
    (2, '2024-01-22'),   -- order 3
    (3, '2024-02-14'),   -- order 4
    (3, '2024-04-01'),   -- order 5
    (4, '2024-02-28'),   -- order 6
    (5, '2024-03-15'),   -- order 7
    (5, '2024-05-10'),   -- order 8
    (5, '2024-06-20'),   -- order 9
    (6, '2024-04-18'),   -- order 10
    (6, '2024-07-02'),   -- order 11
    (7, '2024-05-25'),   -- order 12
    (7, '2024-08-14'),   -- order 13
    (8, '2024-06-30'),   -- order 14
    (8, '2024-09-05');   -- order 15

-- ─── Order Items (23) ───────────────────────────────────────────────────────

INSERT INTO order_items (order_id, book_id, quantity, unit_price) VALUES
    -- order 1: Alice
    (1,  1, 1,  9.99),
    (1,  7, 1,  8.49),
    -- order 2: Alice
    (2,  4, 1, 13.99),
    (2, 10, 1, 10.99),
    -- order 3: Bob
    (3,  2, 2,  7.99),
    -- order 4: Carol
    (4,  5, 1, 11.99),
    (4, 13, 1, 10.49),
    -- order 5: Carol
    (5, 16, 1, 10.99),
    (5, 17, 1, 12.49),
    -- order 6: David
    (6,  8, 1,  8.99),
    (6, 14, 1,  9.99),
    -- order 7: Elena
    (7, 11, 1, 11.49),
    (7, 19, 1, 15.99),
    -- order 8: Elena
    (8,  3, 1, 12.99),
    -- order 9: Elena
    (9,  6, 1, 14.99),
    (9, 18, 1, 13.49),
    -- order 10: Frank
    (10, 9, 1,  9.49),
    -- order 11: Frank
    (11, 15, 1,  8.99),
    (11, 12, 1,  8.99),
    -- order 12: Grace
    (12,  1, 1,  9.99),
    (12, 16, 1, 10.99),
    -- order 13: Grace
    (13, 19, 1, 15.99),
    -- order 14: Henry
    (14,  4, 1, 13.99);
