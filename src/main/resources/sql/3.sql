-- Таблица CUSTOMER (включает адрес)
CREATE TABLE customer (
  customer_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  last_name   VARCHAR(100) NOT NULL,
  first_name  VARCHAR(100) NOT NULL,
  gender      VARCHAR(20),
  address_text VARCHAR(500) NOT NULL
);

-- Таблица FILM
CREATE TABLE film (
  film_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title   VARCHAR(255) NOT NULL
);

-- Таблица RENTAL
CREATE TABLE rental (
  rental_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  film_id     BIGINT NOT NULL,
  CONSTRAINT fk_rental_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
  CONSTRAINT fk_rental_film
    FOREIGN KEY (film_id) REFERENCES film(film_id)
);

-- Полезные индексы под FK (часто ускоряют JOIN/поиск)
CREATE INDEX idx_rental_customer_id  ON rental(customer_id);
CREATE INDEX idx_rental_film_id      ON rental(film_id);
