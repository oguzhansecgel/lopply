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
subscriber_id BIGINT NOT NULL,
is_public BOOLEAN DEFAULT false,

CONSTRAINT fk_subscriber FOREIGN KEY (subscriber_id) REFERENCES Subscriber(id) ON DELETE CASCADE
);

CREATE TABLE Subscriber_PlaylistItem (
id SERIAL PRIMARY KEY,
uuid UUID NOT NULL UNIQUE,
music_id BIGINT NOT NULL,
playlist_id BIGINT NOT NULL,

CONSTRAINT fk_Subscriber_Playlist FOREIGN KEY (playlist_id) REFERENCES Subscriber_Playlist(id) ON DELETE CASCADE
);
