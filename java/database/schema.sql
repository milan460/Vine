BEGIN TRANSACTION;

DROP TABLE IF EXISTS receipt;
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
--lets get up one table at a time here, and go through the life cycle of how a plant is bought and sold start to finish, first, we start with adding it to your virtual garden
CREATE TABLE favorites(
favorites_id SERIAL, --a unique identifier for the plant listing that we have, we can tie this to the actual listing,
-- the way we currently have it set up, is that each one of the plants added has a unique identifying number, this number
-- that ties back to the user, for example, each time a new plant is being added, and it can be two of the same plant, we increment this number up to say "hey, this is a new plant"
plant_id int NOT NULL, --this identifies what plant we have
username varchar(50) NOT NULL, --this associates the plant and favorite_id to the user
owned_plant boolean NOT NULL DEFAULT false, -- this is the an identifier to say, "hey, do you own this plant" this is important so that we can use this value to determine whether or not to add to the next table
 --we will attach a button to the client side of the application in the favorites page (virtual garden) that ,upon call or button press, will update this value to true

CONSTRAINT PK_favorites PRIMARY KEY (favorites_id),
CONSTRAINT FK_favorites FOREIGN KEY (username) REFERENCES users(username)

);
--this table is going to transfer the information from the favorites table, we will execute an insert SQL statement on
 --a button click that is going to create a listing for the plant that is going to pull
 --the values of what we have for the plants that we already have given to us from the favorites table, along with adding additional information on this table so that we can create the listing
CREATE TABLE sellers(
favorites_id int NOT NULL, --this is the unique identifier for the plant from earlier, as stated, this will keep
 --track of what plant is what, its a completely unique identifier that allows us to keep track of how many plants were in the favorites table from earlier
plant_id int NOT NULL, --self explanatory here, this is what we pass to the API to allow us to show what plant we have
username varchar(50) NOT NULL, --this allows us to associate the listing to the table, this way, the user can see all their plants in the listing
price money NOT NULL DEFAULT 0.00, -- this is the price that the user will set to the plant, the user on the client side will have to give the price to the server through a form
is_available boolean NOT NULL DEFAULT false, --we may not need this, but we could have it set that we can edit the price of the plant before actually listing it as available on the marketplace

CONSTRAINT FK_sellers_favorites FOREIGN KEY (favorites_id) REFERENCES favorites(favorites_id), --like i said, this was referencing the individual plant that they have in their virtual garden
-- as a second note, this also makes a bug in the virtual garden where they could add multiple of the same plant to the garden a feature so this is why we are tying these two together
CONSTRAINT FK_sellers FOREIGN KEY (username) REFERENCES users(username)
--same tie as before
);
--after this, if a user decides to buy the plant, here is what we can do, since the favorite ID is still associated with the user, then we can literally
--just update the username associated with it, and it will be placed in their own virtual garden since its tied to that user with its own unique identifier anyway
--we don't have to overcomplicate things in this transaction since its already within the table and we would need to change things in the table anyhow to show that the plant isn't the sellers anymore

--this next table is so we can have a receipt for the transaction that we can display to the user
CREATE TABLE receipt (
receipt_id SERIAL NOT NULL, -- this is going to basically be a rolling list for the receipts, it will be good for system management purposes
from_username varchar(50) NOT NULL, --this is what im thinking for this, before we actually go through with the transaction, we can use a SQL select statement, and grab the original username and then just have the value be what the result of the select statement would be, which would be the original owner who posted the listing.
to_username varchar(50) NOT NULL,  --this is the user who is buying the plant on the client side, we would just use the principal for the value since it will just be the logged in user's username
price money NOT NULL, --this will be the price of the purchase, we can add this into the values of the table the same way that I had mentioned with the from username
plant_id int NOT NULL, --this will be what plant they bought, on the client side, we could take the plant details, and pull the common name of the plant with this data for the receipt


CONSTRAINT PK_receipt PRIMARY KEY (receipt_id),
CONSTRAINT FK_receipt FOREIGN KEY (from_username) REFERENCES users(username)

);




COMMIT TRANSACTION;

