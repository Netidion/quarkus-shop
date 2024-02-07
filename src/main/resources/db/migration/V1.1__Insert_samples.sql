
-- Insert sample data into the 'customers' table
INSERT INTO customers (id, created_date, last_modified, email, enabled, first_name, last_name, telephone)
VALUES
    (3, current_timestamp, current_timestamp, 'john.doe@example.com', true, 'John', 'Doe', '123456789'),
    (4, current_timestamp, current_timestamp, 'jane.smith@example.com', true, 'Jane', 'Smith', '987654321'),
    (5, current_timestamp, current_timestamp, 'max.black@example.com', true, 'Max', 'Black', '2569874563');

-- Insert a record into the 'carts' table for initialization
INSERT INTO carts (id, created_date, last_modified, customer_id, status)
VALUES
    (3,  current_timestamp, current_timestamp, 3, 'NEW'),
    (4,  current_timestamp, current_timestamp, 4, 'NEW'),
    (5,  current_timestamp, current_timestamp, 5, 'CANCELLED');

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
