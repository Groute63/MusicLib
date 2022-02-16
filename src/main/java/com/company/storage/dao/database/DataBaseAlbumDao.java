package com.company.storage.dao.database;

import com.company.entityclass.Album;
import com.company.entityclass.Song;
import com.company.exception.DBException;
import com.company.storage.dao.AlbumDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataBaseAlbumDao implements AlbumDao {

    private final PreparedStatement insertAlbum;
    private final PreparedStatement insertSong;
    private final PreparedStatement insertSongToAlbum;
    private final PreparedStatement insertTag;
    private final PreparedStatement selectAlbum;
    private final PreparedStatement selectSongToAlbum;
    private final PreparedStatement selectSong;
    private final PreparedStatement selectTag;
    private final PreparedStatement deleteAlbumById;
    private final PreparedStatement deleteSongById;
    private final PreparedStatement deleteSongTag;
    private final PreparedStatement deleteSongToAlbum;
    private final PreparedStatement selectAllAlbum;
    private final PreparedStatement renameAlbum;

    public DataBaseAlbumDao(Connection con){
        try {
            insertAlbum = con.prepareStatement("INSERT INTO album VALUES(?,?)");
            insertSong = con.prepareStatement("INSERT INTO song VALUES(?,?,?)");
            insertSongToAlbum = con.prepareStatement("INSERT INTO song_to_album VALUES(?,?)");
            insertTag = con.prepareStatement("INSERT INTO song_tag VALUES(?,?)");
            selectAlbum = con.prepareStatement("SELECT * FROM album where album_id = ?");
            selectAllAlbum = con.prepareStatement("SELECT * FROM album");
            selectSongToAlbum = con.prepareStatement("SELECT song_id FROM song_to_album where album_id = ?");
            selectSong = con.prepareStatement("SELECT * FROM song where song_id = ?");
            selectTag = con.prepareStatement("SELECT tag FROM song_tag where song_id = ?");
            deleteAlbumById = con.prepareStatement(" DELETE FROM album  WHERE album_id = ?");
            deleteSongById = con.prepareStatement(" DELETE FROM song WHERE song_id = ?");
            deleteSongToAlbum = con.prepareStatement(" DELETE FROM song_to_album WHERE album_id = ?");
            deleteSongTag = con.prepareStatement(" DELETE FROM song_tag WHERE song_id = ?");
            renameAlbum = con.prepareStatement("UPDATE album  SET album_name = ?  WHERE album_id = ?");
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }

    @Override
    public Album getAlbumById(UUID id){
        List<String> tag = new ArrayList<>();
        ResultSet albumSet;
        ResultSet songIdSet;
        ResultSet songSet;
        ResultSet tagSet;
        Album alb = null;
        try {
            selectAlbum.setString(1, id.toString());
            albumSet = selectAlbum.executeQuery();
            while (albumSet.next()) {
                alb = new Album(UUID.fromString(albumSet.getString(1)), albumSet.getString(2));
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
            return alb;
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }

    @Override
    public void addAlbum(Album... albums){
        List<Song> songs;
        List<String> tag;
        try {
            for (Album album : albums) {
                songs = album.getSongs();
                insertAlbum.setString(1, album.getId().toString());
                insertAlbum.setString(2, album.getName());
                insertAlbum.execute();
                insertSongToAlbum.setString(2, album.getId().toString());
                for (Song song : songs) {
                    insertSong.setString(1, song.getId().toString());
                    insertSong.setString(2, song.getName());
                    insertSong.setLong(3, song.getLength());
                    insertSong.execute();
                    insertSongToAlbum.setString(1, song.getId().toString());
                    insertSongToAlbum.execute();
                    insertTag.setString(1, song.getId().toString());
                    tag = song.getTags();
                    for (String t : tag) {
                        insertTag.setString(2, t);
                        insertTag.execute();
                    }
                }
            }
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }

    @Override
    public Map<UUID, Album> getAllAlbum(){
        Map<UUID, Album> map = new HashMap<>();
        List<String> tag = new ArrayList<>();
        ResultSet albumSet;
        ResultSet songIdSet;
        ResultSet songSet;
        ResultSet tagSet;
        try {
            albumSet = selectAllAlbum.executeQuery();
            while (albumSet.next()) {
                Album alb = new Album(UUID.fromString(albumSet.getString(1)), albumSet.getString(2));
                map.put(alb.getId(), alb);
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
            return map;
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }

    @Override
    public void deleteAlbumById(UUID id){
        try {
            selectSongToAlbum.setString(1, id.toString());
            ResultSet songIdSet = selectSongToAlbum.executeQuery();
            deleteSongToAlbum.setString(1, id.toString());
            deleteSongToAlbum.execute();
            deleteAlbumById.setString(1, id.toString());
            deleteAlbumById.execute();
            while (songIdSet.next()) {
                deleteSongTag.setString(1, songIdSet.getString(1));
                deleteSongTag.execute();
                deleteSongById.setString(1, songIdSet.getString(1));
                deleteSongById.execute();
            }
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }

    @Override
    public void renameAlbumById(UUID id, String newName){
        try {
            renameAlbum.setString(1, newName);
            renameAlbum.setString(2, id.toString());
            renameAlbum.execute();
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }

    @Override
    public void addNewSongs(UUID albumId, Song... songs){
        try {
            for (Song song : songs) {
                insertSongToAlbum.setString(1, song.getId().toString());
                insertSongToAlbum.setString(2, albumId.toString());
                insertSongToAlbum.execute();
            }
        } catch (SQLException throwable) {
            throw new DBException(throwable.getMessage());
        }
    }
}
