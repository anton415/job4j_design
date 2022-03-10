-- Создание таблиц
create table type(
    id serial primary key,
    name varchar(255)
);

create table product(
    id serial primary key,
    name varchar(255),
    type_id int references type(id),
    expired_date date,
    price int
);

-- Заполнение таблиц
insert into type(name) values ('СЫР');
insert into type(name) values ('ХЛЕБ');
insert into type(name) values ('ЧИПСЫ');

insert into product(name, type_id, expired_date, price) values('Ламбер', 1, date '2022-11-11', 900);
insert into product(name, type_id, expired_date, price) values('Arla Natura', 1, date '2022-11-11', 300);
insert into product(name, type_id, expired_date, price) values('Брест-Литовск', 1, date '2021-11-11', 900);
insert into product(name, type_id, expired_date, price) values('Даниловский', 2, date '2012-11-11', 64);
insert into product(name, type_id, expired_date, price) values('Коломенское', 2, date '2022-01-11', 50);
insert into product(name, type_id, expired_date, price) values('Лейс', 3, date '2022-01-11', 100);
insert into product(name, type_id, expired_date, price) values('Лейс мороженое', 3, date '2022-01-11', 120);

-- 1. Написать запрос получение всех продуктов с типом "СЫР"
select *
from product as p
join type as t
on p.type_id = t.id
where t.name = 'СЫР';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженое"
select *
from product as p
where p.name like '%мороженое%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых уже истек
select *
from product as p
where p.expired_date < current_date;

-- 4. Написать запрос, который выводит самый дорогой продукт.
select *
from product as p
where price = (
  select max(price)
  from product
);

-- 5. Написать запрос, который выводит для каждого типа количество продуктов к нему принадлежащих. В виде имя_типа, количество
select t.name, count(t.name)
from type as t
join product as p
on p.type_id = t.id
group by t.name;

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select *
from product as p
join type as t
on p.type_id = t.id
where t.name = 'СЫР' or t.name = 'МОЛОКО';

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук. Под количеством подразумевается количество продуктов определенного типа. Например, если есть тип "СЫР" и продукты "Сыр плавленный" и "Сыр моцарелла", которые ему принадлежат, то количество продуктов типа "СЫР" будет 2.
select t.name, count(t.name)
from type as t
join product as p
on p.type_id = t.id
group by t.name
having count(t.name) < 10;

-- 8. Вывести все продукты и их тип.
select p.name as продукт, t.name as тип
from product as p
join type as t
on p.type_id = t.id
