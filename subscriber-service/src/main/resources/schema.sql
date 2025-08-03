CREATE TABLE Subscriber (
id SERIAL PRIMARY KEY,
uuid UUID NOT NULL UNIQUE,
account_id BIGINT,
first_name VARCHAR(255),
last_name VARCHAR(255)
);

CREATE TABLE Subscriber_Playlist (
id SERIAL PRIMARY KEY,
uuid UUID NOT NULL UNIQUE,
music_id INT NOT NULL,
subscriber_id BIGINT NOT NULL,
is_public BOOLEAN DEFAULT false,

CONSTRAINT fk_subscriber FOREIGN KEY (subscriber_id) REFERENCES Subscriber(id) ON DELETE CASCADE
);
