-- Task 1
create table EMP_NR (
    ID number primary key,
    NAME varchar(20), 
    SURNAME varchar(20), 
    AGE number, 
    SAL number
);

-- Task 2
alter table EMP_NR add
    (DATE_FROM number, 
     DATE_UNTIL date);
     
-- Task 3
alter table EMP_NR 