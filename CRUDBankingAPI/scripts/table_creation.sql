CREATE TABLE IF NOT EXISTS users (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(40) NOT NULL,
	last_name VARCHAR(40) NOT NULL,
	username VARCHAR(40) UNIQUE NOT NULL,
	password VARCHAR(100) NOT NULL,
	CHECK (char_length(password) >= 8),
	email VARCHAR(40) UNIQUE NOT NULL,
	is_admin boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS accounts (
	account_id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users (user_id),
	balance NUMERIC(12, 2) DEFAULT 0.00,
	title VARCHAR(40) NOT NULL,
	description VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS transactions (
	transaction_id SERIAL PRIMARY KEY,
	account_id INT NOT NULL,
	FOREIGN KEY (account_id) REFERENCES accounts (account_id),
	amount NUMERIC(, 2) NOT NULL,
	date TIMESTAMP DEFAULT DATE_TRUNC('minute,' CURRENT_TIMESTAMP)
);