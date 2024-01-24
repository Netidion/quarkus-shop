create table customers (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, email varchar(255), enabled boolean not null, first_name varchar(255), last_name varchar(255), telephone varchar(255), primary key (id));
create table carts (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, customer_id bigint, status varchar(255) not null, primary key (id));
create table order_items (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, quantity bigint not null, order_id bigint, product_id bigint, primary key (id));
create table orders (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, total_price numeric(10,2) not null, address_1 varchar(255), address_2 varchar(255), city varchar(255), country varchar(2) not null, post_code varchar(10) not null, shipped timestamp(6) with time zone, status varchar(255) not null check (status in ('CREATION','PAID','SHIPPED','DELIVERED','CLOSED')), cart_id bigint, payment_id bigint, primary key (id));
create table payments (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, amount numeric(38,2) not null, e_payment_id varchar(255), status varchar(255) check (status in ('ACCEPTED','PENDING','REFUSED','ERROR')), primary key (id));
create table products (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, description varchar(255) not null, name varchar(255) not null, price numeric(10,2) not null, product_status smallint not null check (product_status between 0 and 1), sales_counter integer, category_id bigint, primary key (id));
create table categories (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, description varchar(255) not null, name varchar(255) not null, primary key (id));
create table products_reviews (product_id bigint not null, reviews_id bigint not null, primary key (product_id, reviews_id));
create table reviews (id bigint not null, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone, description varchar(255) not null, rating bigint not null, title varchar(255) not null, primary key (id));

create sequence customers_SEQ start with 1 increment by 50;
create sequence carts_SEQ start with 1 increment by 50;
create sequence categories_SEQ start with 1 increment by 50;
create sequence order_items_SEQ start with 1 increment by 50;
create sequence orders_SEQ start with 1 increment by 50;
create sequence payments_SEQ start with 1 increment by 50;
create sequence products_SEQ start with 1 increment by 50;
create sequence reviews_SEQ start with 1 increment by 50;

-- Insert sample data into the 'customers' table
INSERT INTO customers (id, created_date, last_modified, email, enabled, first_name, last_name, telephone)
VALUES
    (1, current_timestamp, current_timestamp, 'john.doe@example.com', true, 'John', 'Doe', '123456789'),
    (2, current_timestamp, current_timestamp, 'jane.smith@example.com', true, 'Jane', 'Smith', '987654321');

-- Insert a record into the 'carts' table for initialization
INSERT INTO carts (id, created_date, last_modified, customer_id, status)
VALUES
    (1,  current_timestamp, current_timestamp, 1, 'NEW'),
    (2,  current_timestamp, current_timestamp, 2, 'CANCELLED');

-- Insert sample data into the 'order_items' table
INSERT INTO order_items (id, created_date, last_modified, quantity, order_id, product_id)
VALUES
    (1, current_timestamp, current_timestamp, 2, 1, 101),
    (2, current_timestamp, current_timestamp, 1, 1, 102),
    (3, current_timestamp, current_timestamp, 3, 2, 103);

-- Insert sample data into the 'orders' table
INSERT INTO orders (id, created_date, last_modified, total_price, address_1, address_2, city, country, post_code, shipped, status, cart_id, payment_id)
VALUES
    (1, current_timestamp, current_timestamp, 150.00, '123 Main St', 'Apt 45', 'Cityville', 'US', '12345', current_timestamp, 'PAID', 1, 101),
    (2, current_timestamp, current_timestamp, 75.50, '456 Oak St', 'Suite 22', 'Townsville', 'US', '54321', current_timestamp, 'CREATION', 2, 102);

-- Insert sample data into the 'payments' table
INSERT INTO payments (id, created_date, last_modified, amount, e_payment_id, status)
VALUES
    (101, current_timestamp, current_timestamp, 150.00, 'e123456', 'ACCEPTED'),
    (102, current_timestamp, current_timestamp, 75.50, 'e789012', 'PENDING');

-- Insert sample data into the 'products' table
INSERT INTO products (id, created_date, last_modified, description, name, price, product_status, sales_counter, category_id)
VALUES
    (101, current_timestamp, current_timestamp, 'Product 1 description', 'Product 1', 49.99, 1, 100, 201),
    (102, current_timestamp, current_timestamp, 'Product 2 description', 'Product 2', 29.99, 1, 75, 202),
    (103, current_timestamp, current_timestamp, 'Product 3 description', 'Product 3', 69.99, 0, 150, 203);

-- Insert sample data into the 'customers' table
INSERT INTO categories (id, created_date, last_modified, name, description)
VALUES
    (1, current_timestamp, current_timestamp, 'Category 1', 'Category 1 description'),
    (2, current_timestamp, current_timestamp, 'Category 2', 'Category 2 description'),
    (3, current_timestamp, current_timestamp, 'Category 3', 'Category 3 description');

-- Insert sample data into the 'products_reviews' table
INSERT INTO products_reviews (product_id, reviews_id)
VALUES
    (101, 1),
    (101, 2),
    (102, 3);

-- Insert sample data into the 'reviews' table
INSERT INTO reviews (id, created_date, last_modified, description, rating, title)
VALUES
    (1, current_timestamp, current_timestamp, 'Great product!', 5, 'Excellent'),
    (2, current_timestamp, current_timestamp, 'Fast shipping and good quality.', 4, 'Good'),
    (3, current_timestamp, current_timestamp, 'Not satisfied with the product.', 2, 'Disappointing');
