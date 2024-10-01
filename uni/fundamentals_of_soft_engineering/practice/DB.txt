create table if not exists customers (
	id uuid primary key,
	first_name text not null,
	last_name text not null,
	email text,
	password text,
	address text,
	phone text
);

create table if not exists categories (
	id uuid primary key,
	name text,
	description text
);

create table if not exists cities (
	id uuid primary key,
	name text not null
);

create table if not exists warehouses (
	id uuid primary key,
	city_id uuid references cities(id),
	name text not null,
	exact_location text not null,
	capacity int
);

create table if not exists shipment_mapping (
	city_id uuid references cities(id),
	warehouse_id uuid references warehouses(id)
);

create table if not exists products (
	id uuid primary key,
	sku text,
	name text,
	description text,
	price decimal,
	stock int,
	expiration_date date,
	production_date timestamp,
	category_id uuid references categories(id),
	warehouse_id uuid references warehouses(id)
);

create table if not exists carts (
	id uuid primary key,
	customer_id uuid references customers(id),
	max_capacity int
);

create table if not exists cart_items (
	id uuid primary key,
	product_id uuid references products(id),
	cart_id uuid references carts(id)
);

create table if not exists payments (
	id uuid primary key,
	payment_type text not null,
	payment_date timestamp,
	amount decimal,
	change decimal,
	comission decimal,
	discount decimal
);

create table if not exists orders (
	id uuid primary key,
	customer_id uuid references customers(id),
	status text not null,
	destination_city_id uuid references cities(id),
	exact_destination_location text not null,
	payment_id uuid references payments(id),
	creation_time timestamp,
	last_update_time timestamp
);

create table if not exists order_items (
	id uuid primary key,
	product_id uuid references products(id),
	order_id uuid references orders(id)
);

