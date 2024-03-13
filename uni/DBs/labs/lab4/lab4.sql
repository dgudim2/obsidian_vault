-- Task 1
select ENAME, sal * 1.5 as sal15 from emp;

-- Task 2
select ENAME || '   ' || JOB as EMPLOYEE_AND_JOB from emp;

-- Task 3
select ENAME, add_months(HIREDATE, 12) as REVIEW from emp order by REVIEW asc;

-- Task 4
-- Note: this is incorrect
select ENAME, HIREDATE, floor(INTERVL / 31 / 12)
|| ' YEARS '
|| floor(INTERVL / 31 - floor(INTERVL / 31 / 12) * 12)
|| ' MONTHS' as LENGTH_OF_SERVICE  from (
    select ENAME, HIREDATE, sysdate - HIREDATE as INTERVL from emp
);

-- Task 5
select ENAME, CASE when hireday > 15 then 'Next month' else 'This month' end, hireday as HIRED_AT from (
    select ENAME, extract(DAY from HIREDATE) as hireday from emp
);

-- Task 6
select ENAME || ' (' || initcap(JOB) || ')' as EMPLOYEE from emp;

-- Task 7
SELECT * FROM EMP WHERE UPPER(JOB) = UPPER('&JOB');

-- Task 8
select ENAME, DEPTNO, INITCAP(CASE when lower(JOB) = 'salesman' then 'salesperson' else JOB end) as job from emp;
