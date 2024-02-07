create table abstract_entities (id serial primary key, created_date timestamp(6) with time zone not null, last_modified timestamp(6) with time zone);
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
