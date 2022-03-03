select * from fauna where name like '%fish%';
select * from fauna where 10000 < avg_age and avg_age < 21000;
select * from fauna where discovery_date is null;
select * from fauna where discovery_date < date '1950-01-01';