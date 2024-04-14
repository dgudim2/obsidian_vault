-- Danila Gudim


-- Task 1
select * from NOBEL_WIN where YEAR between 1984 and 1995 order by WINNER asc;

-- Task 2
select WINNER from NOBEL_WIN where LOWER(SUBJECT) = 'literature' and YEAR = 1971;

-- Task 3
select WINNER 
|| ' has won Nobel Prize in ' 
|| YEAR 
|| ' in ' 
|| CATEGORY 
|| ' category' as WINNERS 
from NOBEL_WIN;

-- Task 4
select * from NOBEL_WIN where YEAR in (&years) and LOWER(COUNTRY) = LOWER('&country');

-- Task 5
select WINNER from NOBEL_WIN where LOWER(WINNER) LIKE '%l%';