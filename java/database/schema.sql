BEGIN TRANSACTION;

--DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS sellers;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	is_seller boolean NOT NULL DEFAULT false,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE reviews (
	review_id SERIAL,
	username varchar(50) NOT NULL,
	title varchar(50) NOT NULL,
	review_detail varchar(300) NOT NULL,
	rating int NOT NULL,
    plant_id int NOT NULL,

	CONSTRAINT PK_reviews PRIMARY KEY (review_id),
	CONSTRAINT FK_reviews FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE favorites(
favorites_id SERIAL,
plant_id int NOT NULL,
username varchar(50) NOT NULL,
owned_plant boolean NOT NULL DEFAULT false,


CONSTRAINT PK_favorites PRIMARY KEY (favorites_id),
CONSTRAINT FK_favorites FOREIGN KEY (username) REFERENCES users(username)

);



COMMIT TRANSACTION;

