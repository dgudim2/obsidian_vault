
-- Task 1
SELECT min(sal) FROM emp


--Task 2
SELECT min(sal), max(sal), avg(sal) FROM EMP 


-- Task 3
SELECT max(sal), min(sal), JOB FROM EMP GROUP BY JOB 


-- Task 4
SELECT COUNT(*) FROM EMP WHERE JOB = 'MANAGER'


-- Task 5
SELECT avg(sal - comm) AS AVERAGE_SALARY, avg(sal - comm) * 12 AS AVERAGE_YEARLY_INCOME FROM EMP


-- Task 6
SELECT max(sal) - min(sal) FROM emp


-- Task 7
SELECT DNAME, ECOUNT AS EMPLOYEE_COUNT, LOC FROM (SELECT count(ENAME) AS ECOUNT, DEPTNO FROM EMP GROUP BY DEPTNO) depts_filtered 
LEFT JOIN DEPT ON depts_filtered.DEPTNO = DEPT.DEPTNO WHERE ECOUNT > 3;


-- Task 8
SELECT * FROM (
SELECT ename, empno, mgr, sal, row_number() over(PARTITION BY mgr ORDER BY sal ASC, ename ASC, empno asc) AS rn FROM emp)
WHERE rn = 1 AND sal>1000 AND MGR IS NOT null
ORDER BY ENAME


-- Task 9
SELECT ENAME, TO_CHAR(HIREDATE, 'Month, DD "d." YYYY') AS DATE_HIRE FROM EMP e WHERE DEPTNO = 20;


-- Task 10
SELECT ENAME, (CASE when sal < 1500 THEN 'Below 1500' 
		 		    WHEN sal = 1500 THEN 'On target'
		 		    ELSE CAST(sal AS varchar(20)) END) AS SAL FROM emp
		 		    

-- Task 11
SELECT TO_CHAR(TO_DATE('13.04.2023', 'DD.MM.YYYY'), 'D (DAY)') FROM SYS.DUAL 
