package com.company.storage;

import com.company.entityclass.Album;
import com.company.entityclass.Artist;
import com.company.entityclass.EntityClassMarker;
import com.company.entityclass.Song;
import com.company.storage.dao.AlbumDao;
import com.company.storage.dao.ArtistDao;
import com.company.storage.dao.SongDao;
import com.company.storage.dao.database.DataBaseAlbumDao;
import com.company.storage.dao.database.DataBaseArtistDao;
import com.company.storage.dao.database.DataBaseSongDao;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class DBStorage implements Storage, AutoCloseable {

    private String dbUrl = "jdbc:postgresql://localhost:5432/MUSICLIB3";
    private String dbUserName = "postgres";
    private String dbPassword = "092327asd";
    private Connection con = null;
    private SongDao songs;
    private AlbumDao albums;
    private ArtistDao artists;
    private PreparedStatement selectLogin;

    public DBStorage() {
        try {
            getConnection();
            init();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public DBStorage(String dbUrl, String dbUserName, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        try {
            getConnection();
            init();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private void init() throws SQLException {
        selectLogin = con.prepareStatement("SELECT acc FROM users  WHERE login = ? and password = ?");
    }

    public void getConnection() throws SQLException {
        con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        songs = new DataBaseSongDao(con);
        albums = new DataBaseAlbumDao(con);
        artists = new DataBaseArtistDao(con);
        con.setAutoCommit(false);
    }

    public int login(String login, String password) {
        try {
            password = getHash(password);
            selectLogin.setString(1, login);
            selectLogin.setString(2, password);
            ResultSet res = selectLogin.executeQuery();
            if (res.next()) return res.getInt(1);
        } catch (SQLException | NoSuchAlgorithmException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    public static String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        return String.valueOf(Hex.encodeHex(messageDigest.digest()));
    }

    @Override
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
            default:
                return null;
        }
    }

    @Override
    public void add(EntityClassMarker obj, DaoType type) {
        try {
            switch (type) {
                case ARTIST: {
                    artists.addArtist((Artist) obj);
                    break;
                }
                case ALBUM: {
                    albums.addAlbum((Album) obj);
                    break;
                }
                case SONG: {
                    songs.addSongs((Song) obj);
                    break;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
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

    @Override
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

    @Override
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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void addIn(UUID id, UUID id2, DaoType type) {
        try {
            switch (type) {
                case ALBUM: {
                    albums.getAlbumById(id).addSongs(songs.getSongById(id2));
                    break;
                }
                case ARTIST: {
                    artists.addNewAlbum(id, albums.getAlbumById(id2));
                    break;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
