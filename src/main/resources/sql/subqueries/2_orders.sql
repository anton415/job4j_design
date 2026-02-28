CREATE TABLE orders (
    id          SERIAL PRIMARY KEY,
    amount      INT,
    customer_id INT REFERENCES customers (id)
);

INSERT INTO orders (amount, customer_id)
VALUES (100, 1);
INSERT INTO orders (amount, customer_id)
VALUES (150, 2);
INSERT INTO orders (amount, customer_id)
VALUES (200, 3);
INSERT INTO orders (amount, customer_id)
VALUES (250, 4);
INSERT INTO orders (amount, customer_id)
VALUES (300, 5);

-- запрос, который вернет список пользователей, которые еще не выполнили ни одного заказа. 
-- Используйте подзапрос, для реализации Вам понадобится NOT IN.
SELECT * FROM customers WHERE id NOT IN (SELECT customer_id FROM orders);