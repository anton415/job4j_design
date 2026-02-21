CREATE TABLE products
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50),
    producer VARCHAR(50),
    count    INTEGER DEFAULT 0,
    price    INTEGER
);

-- 1)  Триггер должен срабатывать после вставки данных, для любого товара и просто насчитывать налог на товар (нужно прибавить налог к цене товара). 
-- Действовать он должен не на каждый ряд, а на запрос (statement уровень)
CREATE OR REPLACE FUNCTION calc_tax_after_insert()
RETURNS trigger AS 
$$
    BEGIN
        UPDATE products p
        SET price = p.price * 1.20
        FROM inserted_rows i
        WHERE p.id = i.id;
        RETURN NULL;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_calc_tax_after_insert
    AFTER INSERT ON products
    REFERENCING NEW TABLE AS inserted_rows
    FOR EACH STATEMENT
    EXECUTE FUNCTION calc_tax_after_insert();


-- 2) Триггер должен срабатывать до вставки данных и насчитывать налог на товар (нужно прибавить налог к цене товара). 
-- Здесь используем row уровень.
CREATE OR REPLACE FUNCTION calc_tax_before_insert()
RETURNS trigger AS 
$$
    BEGIN
        NEW.price := NEW.price * 1.20;
        RETURN NEW;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_calc_tax_before_insert
    BEFORE INSERT ON products
    FOR EACH ROW
    EXECUTE FUNCTION calc_tax_before_insert();
    

-- 3) Нужно написать триггер на row уровне, который сразу после вставки продукта в таблицу products, будет заносить имя, цену и текущую дату в таблицу history_of_price.
CREATE TABLE history_of_price
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50),
    price INTEGER,
    date  TIMESTAMP
);

CREATE OR REPLACE FUNCTION log_price_history()
RETURNS trigger AS 
$$
    BEGIN
        INSERT INTO history_of_price (name, price, date)
        VALUES (NEW.name, NEW.price, NOW());
        RETURN NEW;
    END;
$$
LANGUAGE plpgsql;   

CREATE TRIGGER trg_log_price_history
    AFTER INSERT ON products
    FOR EACH ROW
    EXECUTE FUNCTION log_price_history();

