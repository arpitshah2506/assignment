create table if not exists ecom_product(product_id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30) NOT NULL,price FLOAT, description VARCHAR(255));

create table if not exists ecom_product_seller(id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, product_id INT(6), user_id INT(6));
