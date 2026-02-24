-- Основная таблица
CREATE TABLE IF NOT EXISTS products
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50),
    producer VARCHAR(50),
    count    INTEGER DEFAULT 0,
    price    INTEGER
);

-- Наполнение данными, если таблица была пустой
INSERT INTO products (name, producer, count, price) VALUES
('A', 'A', 10, 100),
('B', 'B', 20, 200),
('C', 'C', 30, 300);

-- Выполнение каждой команды по отдельности
BEGIN;

SELECT * FROM products ORDER BY id;
UPDATE products SET price = price + 10 WHERE id = 1;
SELECT * FROM products ORDER BY id;
SAVEPOINT save_point_1;

UPDATE products SET count = count + 10 WHERE id = 1;
SELECT * FROM products ORDER BY id;
SAVEPOINT save_point_2;

DELETE FROM products WHERE id = 2;
SELECT * FROM products ORDER BY id;

ROLLBACK TO SAVEPOINT save_point_2;
SELECT * FROM products ORDER BY id;

ROLLBACK TO SAVEPOINT save_point_1;
SELECT * FROM products ORDER BY id;

COMMIT;
