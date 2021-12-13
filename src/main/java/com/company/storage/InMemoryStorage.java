package com.company.storage;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.entityClass.EntityClassMarker;
import com.company.entityClass.Song;
import com.company.storage.dao.ArtistDao;
import com.company.storage.dao.AlbumDao;
import com.company.storage.dao.SongDao;
import com.company.storage.dao.memory.InMemoryArtistDao;
import com.company.storage.dao.memory.InMemoryAlbumDao;
import com.company.storage.dao.memory.InMemorySongDao;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class InMemoryStorage implements Storage {
    private final SongDao songs = new InMemorySongDao();
    private final AlbumDao albums = new InMemoryAlbumDao();
    private final ArtistDao artists = new InMemoryArtistDao();

    public void save(String file,DaoType type) throws IOException {
        Gson gson = new Gson();
        PrintWriter printWriter = new PrintWriter(file);
        EntityClassMarker[] a = null;
        switch (type) {
            case SONG: {
                a = songs.getAllSongs().values().toArray(new Song[0]);
                break;
            }
            case ALBUM: {
                a = albums.getAllAlbum().values().toArray(new Album[0]);
                break;
            }
            case ARTIST: {
                a = artists.getAllArtist().values().toArray(new Artist[0]);
                break;
            }
        }
        for (int i = 0; i < a.length; i++) {
                String json = gson.toJson(a[i]);
                System.out.println(json);
                printWriter.println(json);
            }
        printWriter.flush();
    }

    public void load(String file,DaoType type) throws IOException {
        Gson gson = new Gson();
        EntityClassMarker[] a = null;
        String[] arr = Files.readAllLines(Paths.get(file)).toArray(new String[0]);
        switch (type) {
            case SONG: {
                for (int i = 0; i < arr.length; i++)
                    songs.addSongs(gson.fromJson(arr[i], Song.class));
                break;
            }
            case ALBUM: {
                for (int i = 0; i < arr.length; i++)
                   albums.addAlbum(gson.fromJson(arr[i], Album.class));
                break;
            }
            case ARTIST: {
                for (int i = 0; i < arr.length; i++)
                    artists.addArtist(gson.fromJson(arr[i], Artist.class));
                break;
            }
        }
    }

    public void add(EntityClassMarker obj, DaoType type) {
        switch (type) {
            case SONG: {
                songs.addSongs((Song) obj);
                break;
            }
            case ALBUM: {
                albums.addAlbum((Album) obj);
                break;
            }
            case ARTIST: {
                artists.addArtist((Artist) obj);
                break;
            }
        }
    }

    public void addIn(int pos, int pos2, DaoType type) {
        switch (type) {
            case ALBUM: {
                albums.getAlbumById(getUuid(pos,DaoType.ALBUM)).addSongs(songs.getSongById(getUuid(pos2,DaoType.SONG)));
                break;
            }
            case ARTIST: {
                artists.getArtistById(getUuid(pos,DaoType.ARTIST)).addAlbums(albums.getAlbumById(getUuid(pos2,DaoType.ALBUM)));
                break;
            }
        }
    }

    public void printAll(DaoType type) {
        switch (type) {
            case SONG: {
                Song[] a = songs.getAllSongs().values().toArray(new Song[0]);
                for (int i = 0; i < a.length; i++)
                    System.out.println(a[i]);
                break;
            }
            case ALBUM: {
                Album[] a = albums.getAllAlbum().values().toArray(new Album[0]);
                for (int i = 0; i < a.length; i++)
                    System.out.println(a[i]);
                break;
            }
            case ARTIST: {
                Artist[] a = artists.getAllArtist().values().toArray(new Artist[0]);
                for (int i = 0; i < a.length; i++)
                    System.out.println(a[i]);
                break;
            }
        }
    }

    public void printName(DaoType type) {
        switch (type) {
            case SONG: {
                Song[] a = songs.getAllSongs().values().toArray(new Song[0]);
                for (int i = 0; i < a.length; i++)
                    System.out.println(i + " - " + a[i].getName());
                break;
            }
            case ALBUM: {
                Album[] a = albums.getAllAlbum().values().toArray(new Album[0]);
                for (int i = 0; i < a.length; i++)
                    System.out.println(i + " - " + a[i].getName());
                break;
            }
            case ARTIST: {
                Artist[] a = artists.getAllArtist().values().toArray(new Artist[0]);
                for (int i = 0; i < a.length; i++)
                    System.out.println(i + " - " + a[i].getName());
                break;
            }
        }
    }

    public void delete(int pos,DaoType type) {
        switch (type) {
            case SONG: {
                songs.deleteSongById(getUuid(pos,DaoType.SONG));
                break;
            }
            case ALBUM: {
                albums.deleteAlbumById(getUuid(pos,DaoType.ALBUM));
                break;
            }
            case ARTIST: {
                artists.deleteArtistById(getUuid(pos,DaoType.ARTIST));
                break;
            }
        }

    }

    private UUID getUuid(int pos,DaoType type) {
        switch (type) {
            case SONG: {
                return songs.getAllSongs().keySet().toArray(new UUID[0])[pos];
            }
            case ALBUM: {
                return albums.getAllAlbum().keySet().toArray(new UUID[0])[pos];
            }
            case ARTIST: {
                return artists.getAllArtist().keySet().toArray(new UUID[0])[pos];
            }
        }
        return null;
    }
}
