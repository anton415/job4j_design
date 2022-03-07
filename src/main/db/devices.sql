-- 2. Заполнить таблицы данными.
insert into devices(name, price) values ('Компьютер', 100000), ('Смартфон', 50000), ('Холодильник', 70000);
insert into people(name) values ('Аня'), ('Ваня'), ('Боря');
insert into devices_people(device_id, people_id) values (1, 1), (1, 2);
insert into devices_people(device_id, people_id) values (2, 1), (2, 3);
insert into devices_people(device_id, people_id) values (3, 1), (3, 2), (3, 3);

-- 3. Используя агрегатные функции вывести среднюю цену устройств.
select avg(price) from devices;

-- 4. Используя группировку вывести для каждого человека среднюю цену его устройств.
select p.name, avg(d.price)
from devices_people as dp
join people as p on dp.people_id = p.id
join devices as d on dp.device_id = d.id
group by p.name;

-- 5. Дополнить запрос п.4. условием, что средняя стоимость устройств должна быть больше 5000.
select p.name, avg(d.price)
from devices_people as dp
join people as p on dp.people_id = p.id
join devices as d on dp.device_id = d.id
group by p.name
having avg(d.price) > 5000;