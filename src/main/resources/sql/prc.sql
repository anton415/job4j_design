-- Основная таблица
CREATE TABLE products
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50),
    producer VARCHAR(50),
    count    INTEGER DEFAULT 0,
    price    INTEGER
);

-- Процедура и функция, которые удаляют записи. 
-- Условие – удаление по id, удалить если количество товара равно 0.
CREATE OR REPLACE PROCEDURE delete_product_if_zero_count(p_id INTEGER)
LANGUAGE plpgsql
AS $$
    BEGIN
        DELETE FROM products
        WHERE id = p_id AND count = 0;
    END;
$$;

CREATE OR REPLACE FUNCTION delete_product_if_zero_count_func(p_id INTEGER)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
    BEGIN
        DELETE FROM products
        WHERE id = p_id AND count = 0;
        RETURN FOUND;
    END;
$$;