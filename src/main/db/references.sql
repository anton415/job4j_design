create table computer(
    id serial primary key,
    name varchar(255)
);

create table account(
    id serial primary key,
    name varchar(255)
);

create table computers_accounts(
    id serial primary key,
    computer_id int references computer(id),
    account_id int references account(id)
);

create table phone_number(
    id serial primary key,
    name varchar(255),
    account_id int references account(id) unique
);

create table input_source(
    id serial primary key,
    name varchar(255),
    computer_id int references computer(id)
);
