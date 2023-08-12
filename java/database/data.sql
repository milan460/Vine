BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('user', 'Awesome', 'I loved it', 5, 1);
INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('user', 'Awful', 'I hate it', 1, 3);
INSERT INTO reviews (username, title, review_detail, rating, plant_id) VALUES ('admin', 'Okay', 'It was ight', 3, 1);

INSERT INTO favorites (plant_id, username, owned_plant, listed_for_sale) VALUES ('1', 'user', false, false);
INSERT INTO favorites (plant_id, username) VALUES ('2', 'user');
INSERT INTO favorites (plant_id, username, owned_plant, listed_for_sale) VALUES ('1', 'admin', false, false);
INSERT INTO favorites (plant_id, username, owned_plant, listed_for_sale) VALUES ('2', 'admin', false, false);

COMMIT TRANSACTION;
