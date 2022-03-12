-- 1. Создать структур данных в базе.
-- Таблицы: Кузов. Двигатель, Коробка передач. Body. Engine, Gearbox.
create table body(
    id serial primary key,
    name varchar(255)
);

create table engine(
    id serial primary key,
    name varchar(255)
);

create table transmission(
    id serial primary key,
    name varchar(255)
);

-- 2. Создать структуру Машина. Машина не может существовать без данных из п.1.
create table car(
    id serial primary key,
    name varchar(255),
    body_id int references body(id),
    engine_id int references engine(id),
    transmission_id int references transmission(id)
);

-- 3. Заполнить таблицы через insert.
insert into body(name) values('hatchback'), ('mini'), ('coupe'), ('crossover'), ('4×4'), ('cabriolet');
insert into engine(name) values('twin-cylinder'), ('three-cylinder'), ('four-cylinder'), ('five-cylinder');
insert into transmission(name) values('manual'), ('automatic');

insert into car(name, body_id, engine_id, transmission_id) values('mazda', 3, 1, 2);
insert into car(name, body_id, engine_id, transmission_id) values('mercedes', 6, 4, 2);
insert into car(name, body_id, engine_id, transmission_id) values('bmw', 5, 3, 1);

-- 4. Вывести список всех машин и все привязанные к ним детали.
select c.name as car, b.name as body, e.name as engine, t.name as transmission
from car as c
join body as b
on c.body_id = b.id
join engine as e
on c.engine_id = e.id
join transmission as t
on c.transmission_id = t.id;

-- 5. Вывести отдельно детали (1 деталь - 1 запрос), которые не используются НИ в одной машине, кузова, двигатели, коробки передач.
select b.name as "not used bodies"
from car as c
right join body as b
on c.body_id = b.id
where c.id is null;

select e.name as "not used engines"
from car as c
right join engine as e
on c.engine_id = e.id
where c.id is null;

select t.name as "not used transmissions"
from car as c
right join transmission as t
on c.transmission_id = t.id
where c.id is null;
