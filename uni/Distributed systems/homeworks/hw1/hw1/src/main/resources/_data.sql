insert into "Shop" (id, title, description) values 
(1, 'shop1', 'description1'),
(2, 'shop2', 'description2');

insert into "User" (id, login, "name", "isAdmin", "password", shop_id, "DTYPE") values
(1, 'test', 'test', false, 'test', 1, 'Customer'),
(2, 'test2', 'test2', true, 'test2', 2, 'Manager');

insert into "Warehouse" (id, address) values
(1, 'test street 1');

insert into "Product" (id, description, price, quantity, title, cart_id, shop_id, warehouse_id)
values (1, 'Test1', 10, 10, 'test title', null, 1, 1);