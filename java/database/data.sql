BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('user', 'Awesome', 'I loved it', 5, 1);
INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('user', 'Awful', 'I hate it', 1, 3);
INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('admin', 'Okay', 'It was ight', 3, 1);

INSERT INTO favorites (plant_id, username, owned_plant) VALUES (1, 'user', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (2, 'user', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (20, 'admin', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (2, 'admin', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (144, 'admin', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (24, 'user', false);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (89, 'user', false);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (10, 'admin', false);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (12, 'user', true);


INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (1,  'buy this plant', 25.00, false, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (2, '', 50.00, true, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (3, 'cool', 15.24, true, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (4, 'test', 256.76, true,1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (5, 'TEST CART PLANT', 500.67, true, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (6, 'TEST CART PLANT 2', 756.76, true, 1);



--INSERT INTO sellers (favorites_id, plant_id, username, price) VALUES (1, 1, 'user', 25.00);
--INSERT INTO sellers(favorites_id, plant_id, username, price) VALUES (21, 'user', 10.50);
----INSERT INTO sellers (favorites_id, plant_id, username, price) VALUES (2, 'user', 2.45);
----INSERT INTO sellers (plant_id, username, price) VALUE


--lets use our data from the favorites table and manipulate it in postman for the dummy data rather than add arbitrary
 --dummy data for now, we can add in some on the client side to flesh out the listing page a little more later/tomorrow, but as for now use what we have and adjust it so it goes through the life cycle story listed on the database


COMMIT TRANSACTION;
