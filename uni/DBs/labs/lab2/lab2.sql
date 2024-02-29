-- Task 1
DEFINE target_table = "DEPT"
DEFINE target_rows = (10, 30)

select * from &target_table where DEPTNO in &target_rows

-- Task 2
DEFINE column_ = 'ename';
select concat(concat(&column_, ' '), 'Surname') from emp;

-- Task 3
DEFINE annual_salary = SAL * 12
select &annual_salary as Annual_salary from emp;

-- Task 4
SELECT 'HELLO WORLD' FROM SYS.DUAL;

-- Task 5
DEFINE cond = "where COMM IS NULL"
SELECT * from emp &cond

-- Task 6
select * from emp where sal between 950 and 2500
select * from emp where sal between &min and &max

-- Task 7
select * from emp where EXTRACT(YEAR FROM HIREDATE) in (1981, 1983, 1984)

-- Task 8
select * from emp where JOB in ('CLERK', 'MANAGER') OR JOB = '&jobs'