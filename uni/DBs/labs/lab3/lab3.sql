-- Task 1
drop table EMP_NR;
create table EMP_NR (
    ID number primary key,
    NAME varchar(20),
    SURNAME varchar(20),
    FAMILY number,
    AGE number,
    SAL number
);

-- Task 2
alter table EMP_NR add
    (DATE_FROM number,
     DATE_UNTIL date);

-- Task 3
ALTER TABLE EMP_NR MODIFY DATE_FROM date;

-- Task 4
-- Note: wtf, oracle, this is an abomination
INSERT INTO EMP_NR (ID, NAME, SURNAME, AGE, SAL) values  (1, 'Name1', 'Suaname1', 10, 1000);
INSERT INTO EMP_NR (ID, NAME, SURNAME, AGE, SAL) values  (2, 'Name2', 'Surname2', 20, 2000);
INSERT INTO EMP_NR (ID, NAME, SURNAME, AGE, SAL) values  (3, 'Name3', 'Surname3', 30, 3000);
INSERT INTO EMP_NR (ID, NAME, SURNAME, AGE, SAL) values  (4, 'Aame4', 'Sarname4', 40, 4000);
INSERT INTO EMP_NR (ID, NAME, SURNAME, AGE, SAL) values  (5, 'Dame5', 'Surname5', 50, 5000);

-- Task 5
update EMP_NR set family = 1;

-- Task 6
update EMP_NR set family = null where id in (1, 2) and SAL in (1000, 1500);

-- Task 7
update EMP_NR set SURNAME = 'Test' where age < 30 and substr(SURNAME, 3, 1) = 'a';

-- Task 8
drop table EMP_0NR;
CREATE TABLE EMP_0NR
  AS (SELECT * FROM EMP_NR where
  (NAME LIKE 'A%' or NAME LIKE 'D%')
  and substr(SURNAME, 2, 1) = 'a'
  and AGE >= 20 AND AGE <= 40
  and SAL != 2000
  and FAMILY is not null);
