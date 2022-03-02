-- Роли.
create table role(
    id serial primary key,
    name varchar(255)
);
-- Пользователи.
create table users(
    id serial primary key,
    name varchar(255),
    role_id int references role(id)
);
-- Права ролей.
create table rules(
    id serial primary key,
    name varchar(255)
);
-- Связь между таблицами Роли и Права ролей.
create table role_rules(
    id serial primary key,
    rules_id int references rules(id),
    role_id int references role(id)
);
-- Состояние заявки.
create table state(
    id serial primary key,
    name varchar(255)
);
-- Категории заявки.
create table category(
    id serial primary key,
    name varchar(255)
);
-- Заявки.
create table item(
    id serial primary key,
    name varchar(255),
    user_id int references users(id),
    category_id int references category(id),
    state_id int references state(id)
);
-- Комментарии Заявок.
create table comments(
    id serial primary key,
    name varchar(255),
    item_id int references item(id)
);
-- Приложенные Файлы.
create table attaches(
    id serial primary key,
    name varchar(255),
    item_id int references item(id)
);
