-- seed_data.sql — CX651 Assignment 14
-- Seed data for the books_server PostgreSQL database.
-- Load AFTER schema.sql.
--
-- Genres: Fiction (4 books), Mystery (3 books), Biography (3 books),
--         Science Fiction (3 books), Historical Fiction (2 books)
-- Total:  15 books, 5 authors

INSERT INTO authors (name, country) VALUES
    ('Toni Morrison',           'USA'),        -- 1
    ('George Orwell',           'UK'),         -- 2
    ('Gabriel García Márquez',  'Colombia'),   -- 3
    ('Kazuo Ishiguro',          'UK'),         -- 4
    ('Agatha Christie',         'UK');         -- 5

INSERT INTO books (author_id, title, genre, pub_year, price) VALUES
    -- Fiction (4 books) — canary answer for Q5: "Fiction query returned 4 books"
    (1, 'Beloved',                        'Fiction',            1987, 10.49),
    (1, 'Song of Solomon',                'Fiction',            1977,  9.99),
    (2, 'Animal Farm',                    'Fiction',            1945,  7.99),
    (3, 'Love in the Time of Cholera',    'Fiction',            1985, 11.49),

    -- Mystery (3 books)
    (5, 'Murder on the Orient Express',   'Mystery',            1934,  8.99),
    (5, 'And Then There Were None',       'Mystery',            1939,  7.99),
    (5, 'The Murder of Roger Ackroyd',    'Mystery',            1926,  8.49),

    -- Biography (3 books)
    (2, 'Homage to Catalonia',            'Biography',          1938, 11.99),
    (1, 'The Source of Self-Regard',      'Biography',          2019, 14.99),
    (4, 'The Unconsoled',                 'Biography',          1995, 12.49),

    -- Science Fiction (3 books)
    (2, 'Nineteen Eighty-Four',           'Science Fiction',    1949, 10.99),
    (3, 'Chronicle of a Death Foretold',  'Science Fiction',    1981,  8.99),
    (4, 'Never Let Me Go',                'Science Fiction',    2005, 11.99),

    -- Historical Fiction (2 books)
    (4, 'The Remains of the Day',         'Historical Fiction', 1989, 13.99),
    (3, 'One Hundred Years of Solitude',  'Historical Fiction', 1967, 10.99);
