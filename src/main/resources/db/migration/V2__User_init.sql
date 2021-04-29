create table if not exists ecom_user(id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, firstname VARCHAR(30) NOT NULL,lastname VARCHAR(30) NOT NULL, user_type INT(1));

-- 1 buyer, 2 seller
insert into ecom_user (firstname, lastname, user_type) values ('Arpit','Shah',1);
insert into ecom_user (firstname, lastname, user_type) values ('Arihant','Infracon',2);
