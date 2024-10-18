-- CREATE USER aramuni WITH SUPERUSER PASSWORD 'aramuni';

-- Database: livraria

-- DROP DATABASE IF EXISTS livraria;

CREATE DATABASE livraria
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE book_entity (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);

INSERT INTO book_entity (title, author) 
VALUES 
    ('O Hobbit', 'J.R.R. Tolkien'),
	('O Senhor dos Anéis', 'J.R.R. Tolkien'),
    ('1984', 'George Orwell'),
    ('A Metamorfose', 'Franz Kafka');

SELECT * FROM book_entity
