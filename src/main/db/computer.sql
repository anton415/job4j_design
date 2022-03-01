create table computer(
     id serial primary key,
     name varchar(255),
     storage bigint,
     isActive boolean
);
insert into computer(name, storage, isactive) values('iMac', 512, true);
update computer set storage = 1000 where name = 'iMac';
select * from computer;
delete from computer where name = 'iMac';
select * from computer;