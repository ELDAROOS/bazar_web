CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id INT REFERENCES categories(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    category_id INT REFERENCES categories(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id INT PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    total DECIMAL(10, 2) NOT NULL
);

CREATE TABLE order_items (
    id INT PRIMARY KEY,
    order_id INT REFERENCES orders(id) ON DELETE CASCADE,
    product_id INT REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE addresses (
    id INT PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    address_line VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL
);

--DROP TABLE users, categories, products, orders, order_items, addresses;

INSERT INTO users (id, name, email, password_hash) VALUES
(1, 'Иван Иванов', 'ivan@example.com', 'hashed_password_1'),
(2, 'Анна Петрова', 'anna@example.com', 'hashed_password_2'),
(3, 'Сергей Сергеев', 'sergey@example.com', 'hashed_password_3');

INSERT INTO categories (id, name, parent_id) VALUES
(1, 'Одежда', NULL),
(2, 'Обувь', NULL),
(3, 'Аксессуары', NULL),
(4, 'Мужская одежда', 1),
(5, 'Женская одежда', 1);

INSERT INTO products (id, name, description, price, stock_quantity, category_id) VALUES
(1, 'Футболка', 'Классическая белая футболка', 19.99, 100, 4),
(2, 'Джинсы', 'Синие джинсы прямого кроя', 39.99, 50, 4),
(3, 'Платье', 'Летнее платье с цветочным принтом', 49.99, 30, 5),
(4, 'Кроссовки', 'Удобные кроссовки для повседневной носки', 59.99, 20, 2),
(5, 'Сумка', 'Стильная сумка на каждый день', 29.99, 40, 3);


INSERT INTO orders (id, user_id, status, total) VALUES
(1, 1, 'Оформлен', 59.98),  -- Иван Иванов
(2, 2, 'Доставлен', 49.99);  -- Анна Петрова

INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES
(1, 1, 1, 1, 19.99),  -- Футболка для заказа Ивана
(2, 1, 2, 1, 39.99),  -- Джинсы для заказа Ивана
(3, 2, 3, 1, 49.99);  -- Платье для заказа Анны

INSERT INTO addresses (id, user_id, address_line, city, state, postal_code, country) VALUES
(1, 1, 'Улица Ленина, 1', 'Москва', 'Москва', '101000', 'Россия'),
(2, 2, 'Улица Пушкина, 2', 'Санкт-Петербург', 'Санкт-Петербург', '190000', 'Россия');
