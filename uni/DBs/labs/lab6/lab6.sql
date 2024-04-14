-- Task 1
select ENAME, DNAME from EMP left join DEPT on EMP.DEPTNO = dept.deptno order by DNAME;


-- Task 2
select ENAME, DNAME, dept.DEPTNO from EMP left join DEPT on EMP.DEPTNO = dept.deptno order by DNAME;


-- Task 3
-- Too lazy, just a bunch of joins
