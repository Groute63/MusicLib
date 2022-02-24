CREATE TABLE IF NOT EXISTS album
(
album_id text PRIMARY KEY NOT NULL,
album_name text NOT NULL
);
CREATE TABLE IF NOT EXISTS artist
(
artist_id text PRIMARY KEY NOT NULL,
artist_name text NOT NULL
);
CREATE TABLE IF NOT EXISTS song
(
song_id text PRIMARY KEY NOT NULL,
songname text  NOT NULL,
lengthmin integer NOT NULL
);
CREATE TABLE IF NOT EXISTS song_tag
(
tag text NOT NULL,
song_id text references song(song_id),
CONSTRAINT song_tag_pk PRIMARY KEY (tag,song_id)--composite key
);
CREATE TABLE IF NOT EXISTS song_to_album
(
song_id text references song(song_id),
album_id text references album(album_id),
CONSTRAINT song_to_album_pk PRIMARY KEY (song_id,album_id)--composite key
);
CREATE TABLE IF NOT EXISTS album_to_artist
(
album_id text references album(album_id),
artist_id text references artist(artist_id),
CONSTRAINT album_to_artist_pk PRIMARY KEY (album_id,artist_id)--composite key
);
CREATE TABLE IF NOT EXISTS users
(
login text PRIMARY KEY NOT NULL,
password text  NOT NULL,
acc integer NOT NULL
);
CREATE TABLE artist_to_user(
artist_id text references artist(artist_id),
user_id text references users(login),
CONSTRAINT user_artist_pk PRIMARY KEY (artist_id,user_id) --composite key
);