CREATE TABLE Roles
(
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(15) NOT NULL,
    role_desc VARCHAR(50)
);

CREATE TABLE UserStates
(
    state_id SERIAL PRIMARY KEY,
    state_name VARCHAR(10) NOT NULL,
    state_desc VARCHAR(50)
);

CREATE TABLE Users
(      
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
	phone_no VARCHAR(10) NOT NULL,
    email VARCHAR(50),
    created_on TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_updated_on TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    state_id INTEGER,
	role_id INTEGER,
	FOREIGN KEY (state_id) REFERENCES UserStates (state_id),
	FOREIGN KEY (role_id) REFERENCES Roles (role_id)
);

CREATE TABLE UserInfo
(
    user_id INTEGER NOT NULL,
    address VARCHAR(25),
	street VARCHAR(25),
	ward VARCHAR(15),
    city VARCHAR(25),
    country VARCHAR(15),
    postal_code VARCHAR(10),
    PRIMARY KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE Categories
(      
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(25) NOT NULL,
    category_desc VARCHAR(255)
);

CREATE TABLE Suppliers(
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(50) NOT NULL,
    phone_no VARCHAR(10) NOT NULL,
	email VARCHAR(50),
    address VARCHAR(25),
	street VARCHAR(25),
	ward VARCHAR(15),
    city VARCHAR(25),
    country VARCHAR(15),
    postal_code VARCHAR(10)
);

CREATE TABLE Products(
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    unit VARCHAR(25),
    price NUMERIC,
    quantity INTEGER,
    is_featured BOOLEAN,
    created_on TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_updated_on TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	supplier_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories (category_id),
	FOREIGN KEY (supplier_id) REFERENCES Suppliers (supplier_id)
);

CREATE TABLE ProductImages(
    image_id SERIAL PRIMARY KEY,
    image_desc VARCHAR(25),
    image_link VARCHAR(100),
    product_id INTEGER,
	FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

CREATE TABLE ProductSupplier(
    product_id INTEGER,
    supplier_id INTEGER,
    PRIMARY KEY (product_id, supplier_id),
    FOREIGN KEY (product_id) REFERENCES Products (product_id),
    FOREIGN KEY (supplier_id) REFERENCES Suppliers (supplier_id)
);

CREATE TABLE Orders(
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE Carts(
    cart_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE CartItems (
    cart_item_id SERIAL PRIMARY KEY,
    cart_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    order_id INTEGER,
    quantity INTEGER,
    is_checkout BOOLEAN,
    FOREIGN KEY (cart_id) REFERENCES Carts (cart_id),
    FOREIGN KEY (order_id) REFERENCES Orders (order_id),
    FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

CREATE TABLE Ratings (
    rating_id SERIAL PRIMARY KEY,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5) NOT NULL, -- Enforce rating between 1 and 5
    rating_desc VARCHAR(150),
    created_on TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    cart_item_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (cart_item_id) REFERENCES CartItems (cart_item_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);