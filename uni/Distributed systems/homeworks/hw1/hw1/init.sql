insert into "Shop" (id, title, description) values 
(1, 'shop1', 'description1'),
(2, 'shop2', 'description2');

insert into "User" (id, login, "name", "isAdmin", "password", shop_id, "DTYPE") values
(1, 'test', 'test', false, 'test', 1, 'Customer'),
(2, 'test2', 'test2', true, 'test2', 2, 'Manager');