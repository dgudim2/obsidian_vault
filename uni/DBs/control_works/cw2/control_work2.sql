-- Task 1
select unique ENAME from projects join emp on projects.MNGR = emp.EMPNO where END_DATE is not null and END_DATE < sysdate


-- Task 2
SELECT ENAME, SAL, (CASE when sal <= (select HISAL from salgrade where grade = 1) THEN 'Low'
		 		    WHEN sal >= (select LOSAL from salgrade where grade = 2) and sal <= (select HISAL from salgrade where grade = 3) THEN 'Average'
		 		    ELSE 'High' END) AS SAL_S FROM emp


-- Task 3
select PPAV, BUDGET from projects join emp on projects.MNGR = emp.EMPNO
where sal between (select LOSAL from salgrade where grade = 4) and (select HISAL from salgrade where grade = 4)


-- Task 4
select max(budget) from projects


-- Task 5
select STATUS,count(STATUS) from projects group by STATUS


-- Task 6
select PPAV, BUDGET from projects where budget > 10000
