create table account(
    id serial primary key,
    name varchar(255),
    isActive boolean
);

create table phone_number(
    id serial primary key,
    name varchar(255),
    account_id int references account(id) unique
);

insert into account(name, isActive) values ('Ivan', true);
insert into account(name, isActive) values ('Petr', true);
insert into account(name, isActive) values ('Anton', false);

insert into phone_number(name, account_id) values('123', 1);
insert into phone_number(name, account_id) values('1235', 2);
insert into phone_number(name, account_id) values('123', 3);


select pn.name as Номер, a.name as Аккаунт, a.isactive as Статус
from phone_number as pn
         join account as a
              on pn.account_id = a.id;

select pn.name as Номер, a.name as Аккаунт, a.isactive as Статус
from phone_number as pn
         join account as a
              on pn.account_id = a.id and a.isactive = true;

select pn.name as Номер, a.name as Аккаунт, a.isactive as Статус
from phone_number as pn
         join account as a
              on pn.account_id = a.id and pn.name = '123';
