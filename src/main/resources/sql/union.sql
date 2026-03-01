CREATE TABLE movies
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    director TEXT
);

CREATE TABLE books
(
    id     SERIAL PRIMARY KEY,
    title  TEXT,
    author TEXT
);

INSERT INTO movies (name, director)
VALUES ('Марсианин', 'Ридли Скотт'),
       ('Матрица', 'Братья Вачовски'),
       ('Властелин колец', 'Питер Джексон'),
       ('Гарри Поттер и узник Азкабана', 'Альфонсо Куарон'),
       ('Железный человек', 'Джон Фавро');

INSERT INTO books (title, author)
VALUES ('Гарри Поттер и узник Азкабана', 'Джоан Роулинг'),
       ('Властелин колец', 'Джон Толкин'),
       ('1984', 'Джордж Оруэлл'),
       ('Марсианин', 'Энди Уир'),
       ('Божественная комедия', 'Данте Алигьери');

-- выведите названия всех фильмов, которые сняты по книге
SELECT m.name
FROM movies m
INTERSECT
SELECT b.title
FROM books b;

-- выведите все названия книг, у которых нет экранизации
SELECT b.title
FROM books b
EXCEPT 
SELECT m.name
FROM movies m;

-- выведите все уникальные названия произведений из таблиц movies и books 
-- (т.е фильмы, которые сняты не по книге, и книги без экранизации)
SELECT m.name FROM movies m
UNION ALL
SELECT b.title FROM books b
EXCEPT SELECT m.name FROM movies m
INTERSECT SELECT b.title FROM books b;