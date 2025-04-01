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

insert into "Comment" ("commentOwner_id", id, "parentComment_id", rating, "whichProductCommented_id", "commentBody", "commentTitle")
values
(1, 1, null, 8, 1, 'comment body 1', 'comment 1'),
(2, 2, 1, 10, 1, 'comment body 2', 'comment 2');