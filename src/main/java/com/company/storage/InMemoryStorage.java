package com.company.storage;

import com.company.entityclass.Album;
import com.company.entityclass.Artist;
import com.company.entityclass.EntityClassMarker;
import com.company.entityclass.Song;
import com.company.exception.DBException;
import com.company.storage.dao.ArtistDao;
import com.company.storage.dao.AlbumDao;
import com.company.storage.dao.SongDao;
import com.company.storage.dao.memory.InMemoryArtistDao;
import com.company.storage.dao.memory.InMemoryAlbumDao;
import com.company.storage.dao.memory.InMemorySongDao;
import com.google.gson.*;

import java.util.*;

public class InMemoryStorage implements Storage {
    private final SongDao songs = new InMemorySongDao();
    private final AlbumDao albums = new InMemoryAlbumDao();
    private final ArtistDao artists = new InMemoryArtistDao();
    //private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /*public void save(String file, DaoType type, UUID... id) throws IOException {
        PrintWriter printWriter = new PrintWriter(file);
        List<EntityClassMarker> a = new ArrayList<>();
        switch (type) {
            case SONG: {
                for (UUID uuid : id) {
                    a.add(songs.getSongById(uuid));
                }
                break;
            }
            case ALBUM: {
                for (UUID uuid : id) {
                    a.add(albums.getAlbumById(uuid));
                }
                break;
            }
            case ARTIST: {
                for (UUID uuid : id) {
                    a.add(artists.getArtistById(uuid));
                }
                break;
            }
        }
        String json = gson.toJson(a);
        printWriter.println(json);
        printWriter.flush();
    }*/

   /* public void load(String file, DaoType type) throws IOException {
        List<String> arr = Files.readAllLines(Paths.get(file));
        StringBuilder s = new StringBuilder();
        for (String value : arr)
            s.append(value);
        switch (type) {
            case SONG: {
                Song[] a = (gson.fromJson(s.toString(), Song[].class));
                for (Song v : a)
                    if (v.getId() == null)
                        v.setId(UUID.randomUUID());
                songs.addSongs(a);
                break;
            }
            case ALBUM: {
                Album[] a = (gson.fromJson(s.toString(), Album[].class));
                for (Album v : a)
                    if (v.getId() == null)
                        v.setId(UUID.randomUUID());
                albums.addAlbum(a);
                break;
            }
            case ARTIST: {
                Artist[] a = (gson.fromJson(s.toString(), Artist[].class));
                for (Artist v : a)
                    if (v.getId() == null)
                        v.setId(UUID.randomUUID());
                artists.addArtist(a);
                break;
            }
        }
    }*/

    public Map<UUID, ?> get(DaoType type) {
        switch (type) {
            case SONG: {
                return songs.getAllSongs();
            }
            case ALBUM: {
                return albums.getAllAlbum();
            }
            case ARTIST: {
                return artists.getAllArtist();
            }
        }
        return null;
    }

    public void add(EntityClassMarker obj, DaoType type) {
        try {
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
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void addIn(UUID id, UUID id2, DaoType type) {
        switch (type) {
            case ALBUM: {
                albums.getAlbumById(id).addSongs(songs.getSongById(id2));
                break;
            }
            case ARTIST: {
                artists.getArtistById(id).addAlbums(albums.getAlbumById(id2));
                break;
            }
        }
    }

    public void printAll(DaoType type) {
        switch (type) {
            case SONG: {
                Collection<Song> a = songs.getAllSongs().values();
                for (Song s : a)
                    System.out.println(s);
                break;
            }
            case ALBUM: {
                Collection<Album> a = albums.getAllAlbum().values();
                for (Album b : a)
                    System.out.println(b);
                break;
            }
            case ARTIST: {
                Collection<Artist> a = artists.getAllArtist().values();
                for (Artist b : a)
                    System.out.println(b);
                break;
            }
        }
    }

    public void printName(DaoType type) {
        int i = 0;
        switch (type) {
            case SONG: {
                Collection<Song> b = songs.getAllSongs().values();
                for (Song a : b) {
                    System.out.println(i + " - " + a.getName());
                    i++;
                }
                break;
            }
            case ALBUM: {
                Collection<Album> b = albums.getAllAlbum().values();
                for (Album a : b) {
                    System.out.println(i + " - " + a.getName());
                    i++;
                }
                break;
            }
            case ARTIST: {
                Collection<Artist> b = artists.getAllArtist().values();
                for (Artist a : b) {
                    System.out.println(i + " - " + a.getName());
                    i++;
                }
                break;
            }
        }
    }

    public void delete(UUID id, DaoType type) {
        try {
            switch (type) {
                case SONG: {
                    songs.deleteSongById(id);
                    break;
                }
                case ALBUM: {
                    albums.deleteAlbumById(id);
                    break;
                }
                case ARTIST: {
                    artists.deleteArtistById(id);
                    break;
                }
            }
        }
        catch (Exception e) {
            throw new DBException(e);
        }
    }
}
