CREATE TABLE customers (
    id         SERIAL PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT,
    age        INT,
    country    TEXT
);

INSERT INTO customers (first_name, last_name, age, country)
VALUES ('John', 'Doe', 30, 'USA');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Jane', 'Smith', 25, 'UK');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Alice', 'Johnson', 28, 'Canada');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Bob', 'Brown', 35, 'Australia');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Charlie', 'Davis', 22, 'USA');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Emily', 'Wilson', 27, 'UK');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('David', 'Miller', 32, 'Canada');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Sarah', 'Taylor', 29, 'Australia');
INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Michael', 'Anderson', 31, 'USA');

-- запрос, который вернет список клиентов, возраст которых является минимальным
SELECT * FROM customers WHERE age = (SELECT MIN(age) FROM customers);