-- Основная таблица
CREATE TABLE products
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50),
    producer VARCHAR(50),
    count    INTEGER DEFAULT 0,
    price    INTEGER
);

-- Вкладка 1
BEGIN ISOLATION LEVEL SERIALIZABLE;
SHOW transaction_isolation;
SELECT id, count FROM products WHERE id IN (1,2) ORDER BY id;
UPDATE products SET count = 0 WHERE id = 1;

-- Вкладка 2
BEGIN ISOLATION LEVEL SERIALIZABLE;
SHOW transaction_isolation;
SELECT id, count FROM products WHERE id IN (1,2) ORDER BY id;
UPDATE products SET count = 0 WHERE id = 2;
COMMIT;

-- Вкладка 1
COMMIT;

-- Ошибка:
-- ERROR:  could not serialize access due to read/write dependencies among transactions
-- Reason code: Canceled on identification as a pivot, during commit attempt. 
-- SQL state: 40001
-- Detail: Reason code: Canceled on identification as a pivot, during commit attempt.
-- Hint: The transaction might succeed if retried.