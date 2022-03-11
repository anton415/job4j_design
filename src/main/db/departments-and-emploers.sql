-- 1. Создать таблицы и заполнить их начальными данными
create table departments(
    id serial primary key,
    name varchar(255)
);

create table emploers(
    id serial primary key,
    name varchar(255),
    departments_id int references departments(id)
);

insert into departments(name) values('code');
insert into departments(name) values('test');
insert into departments(name) values('finance');
insert into departments(name) values('db');

insert into emploers(name, departments_id) values('Anton', 1);
insert into emploers(name, departments_id) values('Petr', 1);
insert into emploers(name, departments_id) values('Ivan', 2);
insert into emploers(name, departments_id) values('Stepan', 3);

-- 2. Выполнить запросы с left, rigth, full, cross соединениями
select *
from departments d
left join emploers e
on e.departments_id = d.id;

select *
from departments d
rigth join emploers e
on e.departments_id = d.id;

select *
from departments d
full join emploers e
on e.departments_id = d.id;

select *
from departments d
cross join emploers e;

-- 3. Используя left join найти департаменты, у которых нет работников
select *
from departments d
left join emploers e
on e.departments_id = d.id
where e.id is null;

-- 4. Используя left и right join написать запросы, которые давали бы одинаковый результат (порядок вывода колонок в эти запросах также должен быть идентичный).
select d.name as departments, e.name as emploers
from departments as d
left join emploers as e
on e.departments_id = d.id;

select d.name as departments, e.name as emploers
from emploers as e
RIGHT join departments as d
on d.id = e.departments_id;

-- 5. Создать таблицу teens с атрибутами name, gender и заполнить ее. Используя cross join составить все возможные разнополые пары
CREATE TABLE teens(
	id serial PRIMARY KEY,
  	name VARCHAR(255),
  	GENDER VARCHAR(255)
);

insert into teens(name, gender) values('Ivan', 'Male');
insert into teens(name, gender) values('Vera', 'Female');
insert into teens(name, gender) values('Alex', 'Transgender');
insert into teens(name, gender) values('Max', 'Neutral');

select t1.name, t2.gender
from teens as t1
cross join teens as t2;
