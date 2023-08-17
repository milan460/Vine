BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('parker','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('steve','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('parker', 'Awesome', 'I loved it', 5, 1);
INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('parker', 'Awful', 'I hate it', 1, 3);
INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('steve', 'Okay', 'It was ok', 3, 1);

INSERT INTO favorites (plant_id, username, owned_plant) VALUES (1, 'parker', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (2, 'parker', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (20, 'steve', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (144, 'steve', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (24, 'parker', false);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (89, 'parker', false);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (10, 'steve', true);
INSERT INTO favorites (plant_id, username, owned_plant) VALUES (12, 'parker', true);


INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (3,  'this is an amazing plant', 25.00, true, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (2, 'You can effortlessly add a touch of nature elegance to your home with this plant', 50.00, true, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (4, 'This is an exquisite botanical masterpiece', 256.76, true,1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (5, 'Selling my beautiful plants', 500.67, true, 1);
INSERT INTO sellers (favorites_id, description, price, is_available, stock_quantity) VALUES (7, 'I would love to sell this beautiful plant', 756.76, true, 1);



--INSERT INTO sellers (favorites_id, plant_id, username, price) VALUES (1, 1, 'user', 25.00);
--INSERT INTO sellers(favorites_id, plant_id, username, price) VALUES (21, 'user', 10.50);
----INSERT INTO sellers (favorites_id, plant_id, username, price) VALUES (2, 'user', 2.45);
----INSERT INTO sellers (plant_id, username, price) VALUE


--lets use our data from the favorites table and manipulate it in postman for the dummy data rather than add arbitrary
 --dummy data for now, we can add in some on the client side to flesh out the listing page a little more later/tomorrow, but as for now use what we have and adjust it so it goes through the life cycle story listed on the database


COMMIT TRANSACTION;
