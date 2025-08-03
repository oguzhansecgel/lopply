CREATE TABLE Subscriber (
id SERIAL PRIMARY KEY,
uuid UUID NOT NULL UNIQUE,
account_id BIGINT,
first_name VARCHAR(255),
last_name VARCHAR(255)
);
