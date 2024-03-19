INSERT INTO categories(name, icon, is_active) VALUES('Main course', 'restaurant-menu',true);
INSERT INTO categories(name, icon, is_active) VALUES('Burgers', 'icecream', true);
INSERT INTO categories(name, icon, is_active) VALUES('Hamburguers', 'lunch_dining',true);
INSERT INTO categories(name, icon, is_active) VALUES('Pizzas', 'local_pizza',true);
INSERT INTO categories(name, icon, is_active) VALUES('Drinks', 'local_bar',true);

INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Cheese Hamburger','Delicious hamburger',4.4,5,'',true,0.1,2);
INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Special Pizza','Delicious hamburger',4.4,5,'',false,0.1,2);
INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Mix Pizza','Delicious hamburger',4.4,5,'',true,0.1,2);
INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Chicken Pizza','Delicious hamburger',4.4,5,'',true,0.1,1);
INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Lomo Pizza','Delicious hamburger',4.4,5,'',true,0.1,1);
INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Roasted Chicken','Delicious hamburger',4.4,5,'',true,0.1,1);
INSERT INTO products(name,description,price,stock,image_url,is_active,discount,category_id) VALUES('Ceviche','Delicious hamburger',4.4,5,'',true,0.1,1);

-- Password del 1 al 8
INSERT INTO users(firstname, lastname, dni, phone_number, email, password) VALUES('ANDERSON','BENGOLEA','70267159','975199130','ander@gmail.com','$2a$12$SB4gFOHLZ5.FE2uojL1mmethm5E/C066p9P38DIFGyCVB.rlKuQja');
INSERT INTO roles(name) VALUES('ADMIN');
INSERT INTO user_roles(user_id, role_id) VALUES(1,1);