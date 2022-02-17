package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDInitialization {
    public static void main(String[] args) {
        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/MUSICLIB3";
            String dbUserName = "postgres";
            String dbPassword = "092327asd";
            Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);

            con.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS public.album\n" +
                            "(\n" +
                            "album_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "album_name text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "CONSTRAINT album_pkey PRIMARY KEY (album_id)\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.artist\n" +
                            "(\n" +
                            "artist_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "artist_name text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "CONSTRAINT artist_pkey PRIMARY KEY (artist_id)\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.song\n" +
                            "(\n" +
                            "song_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "songname text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "lengthmin integer NOT NULL,\n" +
                            "CONSTRAINT song_pkey PRIMARY KEY (song_id)\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.song_tag\n" +
                            "(\n" +
                            "song_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "tag text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "CONSTRAINT song_tag_pk PRIMARY KEY (song_id, tag),\n" +
                            "CONSTRAINT song_tag_song_id_fkey FOREIGN KEY (song_id)\n" +
                            "REFERENCES public.song (song_id) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.song_to_album\n" +
                            "(\n" +
                            "song_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "album_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "CONSTRAINT song_album_pk PRIMARY KEY (song_id, album_id),\n" +
                            "CONSTRAINT song_to_album_album_id_fkey FOREIGN KEY (album_id)\n" +
                            "REFERENCES public.album (album_id) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION,\n" +
                            "CONSTRAINT song_to_album_song_id_fkey FOREIGN KEY (song_id)\n" +
                            "REFERENCES public.song (song_id) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.album_to_artist\n" +
                            "(\n" +
                            "album_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "artist_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "CONSTRAINT album_to_artist_pk PRIMARY KEY (album_id, artist_id),\n" +
                            "CONSTRAINT album_to_artist_album_id_fkey FOREIGN KEY (album_id)\n" +
                            "REFERENCES public.album (album_id) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION,\n" +
                            "CONSTRAINT album_to_artist_artist_id_fkey FOREIGN KEY (artist_id)\n" +
                            "REFERENCES public.artist (artist_id) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.users\n" +
                            "(\n" +
                            "login text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "password text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "acc integer,\n" +
                            "CONSTRAINT users_pkey PRIMARY KEY (login)\n" +
                            ");\n" +
                            "CREATE TABLE IF NOT EXISTS public.artist_to_user\n" +
                            "(\n" +
                            "artist_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "user_id text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                            "CONSTRAINT user_artist_pk PRIMARY KEY (artist_id, user_id),\n" +
                            "CONSTRAINT artist_to_user_artist_id_fkey FOREIGN KEY (artist_id)\n" +
                            "REFERENCES public.artist (artist_id) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION,\n" +
                            "CONSTRAINT artist_to_user_user_id_fkey FOREIGN KEY (user_id)\n" +
                            "REFERENCES public.users (login) MATCH SIMPLE\n" +
                            "ON UPDATE NO ACTION\n" +
                            "ON DELETE NO ACTION\n" +
                            ");"
            );
            con.createStatement().execute("\"INSERT INTO users VALUES('admin','admin')");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
