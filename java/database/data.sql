BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO reviews (username, title, review_detail, rating) VALUES ('user', 'Awesome', 'I loved it', 5);
INSERT INTO reviews (username, title, review_detail, rating) VALUES ('user', 'Awful', 'I hate it', 1);
INSERT INTO reviews (username, title, review_detail, rating) VALUES ('admin', 'Okay', 'It was ight', 3);

COMMIT TRANSACTION;
