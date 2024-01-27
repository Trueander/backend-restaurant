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