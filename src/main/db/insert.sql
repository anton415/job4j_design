insert into role(name) values('admin');
insert into role(name) values('user');

insert into users(name, role_id) values('Petr', 1);
insert into users(name, role_id) values('Anton', 2);

insert into rules(name) values('Work');
insert into rules(name) values('Sleep');

insert into role_rules(rules_id, role_id) values(1, 1);
insert into role_rules(rules_id, role_id) values(2, 2);

insert into state(name) values('Doing');
insert into state(name) values('Done');

insert into category(name) values('Important');
insert into category(name) values('Usual');

insert into item(name, user_id, category_id, state_id) values('Test', 2, 2, 1);
insert into item(name, user_id, category_id, state_id) values('Coding', 2, 1, 1);

insert into comments(name, item_id) values('Test comment', 1);
insert into comments(name, item_id) values('Java coding', 2);

insert into attaches(name, item_id) values('Test file', 1);
insert into attaches(name, item_id) values('Important java class', 2);