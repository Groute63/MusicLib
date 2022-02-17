package com.company.storage.dao.database;

import com.company.entityclass.Album;
import com.company.entityclass.Artist;
import com.company.entityclass.Song;
import com.company.exception.DBException;
import com.company.storage.dao.ArtistDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataBaseArtistDao implements ArtistDao {

    private final PreparedStatement insertArtist;
    private final PreparedStatement insertAlbum;
    private final PreparedStatement insertAlbumToArtist;
    private final PreparedStatement insertSong;
    private final PreparedStatement insertSongToAlbum;
    private final PreparedStatement insertSongTags;
    private final PreparedStatement selectAlbumToArtist;
    private final PreparedStatement selectAlbum;
    private final PreparedStatement selectArtist;
    private final PreparedStatement selectSongToAlbum;
    private final PreparedStatement selectSong;
    private final PreparedStatement selectTag;
    private final PreparedStatement selectAllArtist;
    private final PreparedStatement deleteArtistById;
    private final PreparedStatement deleteAlbumById;
    private final PreparedStatement deleteSongById;
    private final PreparedStatement deleteSongTag;
    private final PreparedStatement deleteSongToAlbum;
    private final PreparedStatement deleteAlbumToArtist;
    private final PreparedStatement renameArtist;
    private final Connection con;

    public DataBaseArtistDao(Connection con){
        try {
            this.con = con;
            insertArtist = con.prepareStatement("INSERT INTO artist VALUES(?,?)");
            insertAlbum = con.prepareStatement("INSERT INTO album VALUES(?,?)");
            insertAlbumToArtist = con.prepareStatement("INSERT INTO album_to_artist VALUES(?,?)");
            insertSong = con.prepareStatement("INSERT INTO song VALUES(?,?,?)");
            insertSongToAlbum = con.prepareStatement("INSERT INTO song_to_album VALUES(?,?)");
            insertSongTags = con.prepareStatement("INSERT INTO song_tag VALUES(?,?)");
            selectAlbumToArtist = con.prepareStatement("SELECT album_id FROM album_to_artist where artist_id = ?");
            selectAlbum = con.prepareStatement("SELECT * FROM album where album_id = ?");
            selectArtist = con.prepareStatement("SELECT * FROM artist  WHERE artist_id = ?");
            selectSongToAlbum = con.prepareStatement("SELECT song_id FROM song_to_album where album_id = ?");
            selectSong = con.prepareStatement("SELECT * FROM song where song_id = ?");
            selectTag = con.prepareStatement("SELECT tag FROM song_tag where song_id = ?");
            selectAllArtist = con.prepareStatement("SELECT * FROM artist");
            deleteArtistById = con.prepareStatement(" DELETE FROM artist  WHERE artist_id = ?");
            deleteAlbumById = con.prepareStatement(" DELETE FROM album  WHERE album_id = ?");
            deleteSongById = con.prepareStatement(" DELETE FROM song WHERE song_id = ?");
            deleteSongToAlbum = con.prepareStatement(" DELETE FROM song_to_album WHERE album_id = ?");
            deleteAlbumToArtist = con.prepareStatement(" DELETE FROM album_to_artist WHERE artist_id = ?");
            deleteSongTag = con.prepareStatement(" DELETE FROM song_tag WHERE song_id = ?");
            renameArtist = con.prepareStatement("UPDATE artist  SET artist_name = ?  WHERE artist_id = ?");
        } catch (SQLException throwable) {
            throw new DBException(throwable);
        }
    }

    @Override
    public Artist getArtistById(UUID id){
        List<String> tag = new ArrayList<>();
        ResultSet artistSet;
        ResultSet albumIdSet;
        ResultSet albumSet;
        ResultSet songIdSet;
        ResultSet songSet;
        ResultSet tagSet;
        try {
            Artist art = null;
            selectArtist.setString(1, id.toString());
            artistSet = selectArtist.executeQuery();
            while (artistSet.next()) {
                art = new Artist(id, artistSet.getString(2));
                selectAlbumToArtist.setString(1, id.toString());
                albumIdSet = selectAlbumToArtist.executeQuery();
                while (albumIdSet.next()) {
                    selectAlbum.setString(1, albumIdSet.getString(1));
                    albumSet = selectAlbum.executeQuery();
                    while (albumSet.next()) {
                        Album alb = new Album(UUID.fromString(albumSet.getString(1)), albumSet.getString(2));
                        art.addAlbums(alb);
                        selectSongToAlbum.setString(1, albumSet.getString(1));
                        songIdSet = selectSongToAlbum.executeQuery();
                        while (songIdSet.next()) {
                            selectSong.setString(1, songIdSet.getString(1));
                            songSet = selectSong.executeQuery();
                            while (songSet.next()) {
                                selectTag.setString(1, songSet.getString(1));
                                tagSet = selectTag.executeQuery();
                                tag.clear();
                                while (tagSet.next()) {
                                    tag.add(tagSet.getString(1));
                                }
                                Song song = new Song(UUID.fromString(songSet.getString(1)), songSet.getString(2), songSet.getLong(3), tag.toArray(new String[0]));
                                alb.addSongs(song);
                            }
                        }
                    }
                }
            }
            return art;
        } catch (SQLException throwable) {
            throw new DBException(throwable);
        }
    }

    @Override
    public void addArtist(Artist... artists) throws SQLException {
        List<Album> albums;
        List<Song> songs;
        List<String> tag;
        try {
            for (Artist artist : artists) {
                albums = artist.getAlbums();
                insertArtist.setString(1, artist.getId().toString());
                insertArtist.setString(2, artist.getName());
                insertArtist.execute();
                insertAlbumToArtist.setString(2, artist.getId().toString());
                for (Album album : albums) {
                    songs = album.getSongs();
                    insertAlbum.setString(1, album.getId().toString());
                    insertAlbum.setString(2, album.getName());
                    insertAlbum.execute();
                    insertAlbumToArtist.setString(1, album.getId().toString());
                    insertAlbumToArtist.execute();
                    insertSongToAlbum.setString(2, album.getId().toString());
                    for (Song song : songs) {
                        insertSong.setString(1, song.getId().toString());
                        insertSong.setString(2, song.getName());
                        insertSong.setLong(3, song.getLength());
                        insertSong.execute();
                        insertSongToAlbum.setString(1, song.getId().toString());
                        insertSongToAlbum.execute();
                        insertSongTags.setString(1, song.getId().toString());
                        tag = song.getTags();
                        for (String t : tag) {
                            insertSongTags.setString(2, t);
                            insertSongTags.execute();
                        }
                    }
                }
                con.commit();
            }
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }
    }

    @Override
    public Map<UUID, Artist> getAllArtist(){
        Map<UUID, Artist> map = new HashMap<>();
        List<String> tag = new ArrayList<>();
        UUID artId;
        ResultSet artistSet;
        ResultSet albumIdSet;
        ResultSet albumSet;
        ResultSet songIdSet;
        ResultSet songSet;
        ResultSet tagSet;
        try {
            artistSet = selectAllArtist.executeQuery();
            while (artistSet.next()) {
                artId = UUID.fromString(artistSet.getString(1));
                map.put(artId, new Artist(artId, artistSet.getString(2)));
                selectAlbumToArtist.setString(1, artId.toString());
                albumIdSet = selectAlbumToArtist.executeQuery();
                while (albumIdSet.next()) {
                    selectAlbum.setString(1, albumIdSet.getString(1));
                    albumSet = selectAlbum.executeQuery();
                    while (albumSet.next()) {
                        Album alb = new Album(UUID.fromString(albumSet.getString(1)), albumSet.getString(2));
                        map.get(artId).addAlbums(alb);
                        selectSongToAlbum.setString(1, albumIdSet.getString(1));
                        songIdSet = selectSongToAlbum.executeQuery();
                        while (songIdSet.next()) {
                            selectSong.setString(1, songIdSet.getString(1));
                            songSet = selectSong.executeQuery();
                            while (songSet.next()) {
                                selectTag.setString(1, songSet.getString(1));
                                tagSet = selectTag.executeQuery();
                                tag.clear();
                                while (tagSet.next()) {
                                    tag.add(tagSet.getString(1));
                                }
                                Song song = new Song(UUID.fromString(songSet.getString(1)), songSet.getString(2), songSet.getLong(3), tag.toArray(new String[0]));
                                alb.addSongs(song);
                            }
                        }
                    }
                }
            }
        } catch (SQLException throwable) {
            throw new DBException(throwable);
        }
        return map;
    }

    @Override
    public void deleteArtistById(UUID id) throws SQLException {
        try {
            selectAlbumToArtist.setString(1, id.toString());
            ResultSet albumIdSet = selectAlbumToArtist.executeQuery();
            deleteAlbumToArtist.setString(1, id.toString());
            deleteAlbumToArtist.execute();
            deleteArtistById.setString(1, id.toString());
            deleteArtistById.execute();
            while (albumIdSet.next()) {
                selectSongToAlbum.setString(1, albumIdSet.getString(1));
                ResultSet songIdSet = selectSongToAlbum.executeQuery();
                deleteSongToAlbum.setString(1, albumIdSet.getString(1));
                deleteSongToAlbum.execute();
                deleteAlbumById.setString(1, albumIdSet.getString(1));
                deleteAlbumById.execute();
                while (songIdSet.next()) {
                    deleteSongTag.setString(1, songIdSet.getString(1));
                    deleteSongTag.execute();
                    deleteSongById.setString(1, songIdSet.getString(1));
                    deleteSongById.execute();
                }
            }
            con.commit();
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }
    }

    @Override
    public void renameArtistById(UUID id, String newName) throws SQLException {
        try {
            renameArtist.setString(1, newName);
            renameArtist.setString(2, id.toString());
            renameArtist.execute();
            con.commit();
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }
    }

    @Override
    public void addNewAlbum(UUID artistId, Album... albums) throws SQLException {
        try {
            for (Album album : albums) {
                insertAlbumToArtist.setString(1, album.getId().toString());
                insertAlbumToArtist.setString(2, artistId.toString());
                insertAlbumToArtist.execute();
            }
            con.commit();
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }
    }
}
