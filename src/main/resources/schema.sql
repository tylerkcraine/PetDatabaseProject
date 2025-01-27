DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS users;
CREATE TABLE users (id SERIAL PRIMARY KEY, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, email VARCHAR(321) NOT NULL UNIQUE);
CREATE TABLE pets (id SERIAL PRIMARY KEY, name VARCHAR(255), species VARCHAR(255), breed VARCHAR(255), owner_id INT REFERENCES users(id));