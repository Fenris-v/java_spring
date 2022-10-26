DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    author_id INT          NOT NULL,
    title    VARCHAR(250) NOT NULL,
    price_old VARCHAR(250) DEFAULT NULL,
    price    VARCHAR(250) DEFAULT NULL
);

DROP TABLE IF EXISTS authors;

CREATE TABLE authors
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
);

ALTER TABLE IF EXISTS books
    ADD CONSTRAINT author_book_fk FOREIGN KEY (author_id) REFERENCES authors ON DELETE CASCADE;
