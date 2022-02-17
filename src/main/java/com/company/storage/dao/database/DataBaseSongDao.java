package com.company.storage.dao.database;

import com.company.entityclass.Song;
import com.company.exception.DBException;
import com.company.storage.dao.SongDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataBaseSongDao implements SongDao {

    private final PreparedStatement insertSong;
    private final PreparedStatement insertTag;
    private final PreparedStatement selectSong;
    private final PreparedStatement selectTag;
    private final PreparedStatement deleteSongById;
    private final PreparedStatement deleteSongTag;
    private final PreparedStatement selectAllSong;
    private final PreparedStatement renameSong;
    private final Connection con;

    public DataBaseSongDao(Connection con) {
        try {
            this.con = con;
            insertSong = con.prepareStatement("INSERT INTO song VALUES(?,?,?)");
            insertTag = con.prepareStatement("INSERT INTO song_tag VALUES(?,?)");
            selectSong = con.prepareStatement("SELECT * FROM song where song_id = ?");
            selectTag = con.prepareStatement("SELECT tag FROM song_tag where song_id = ?");
            selectAllSong = con.prepareStatement("SELECT * FROM song");
            deleteSongById = con.prepareStatement(" DELETE FROM song WHERE song_id = ?");
            deleteSongTag = con.prepareStatement(" DELETE FROM song_tag WHERE song_id = ?");
            renameSong = con.prepareStatement("UPDATE song  SET song_name = ?  WHERE song_id = ?");
        } catch (SQLException throwable) {
            throw new DBException(throwable);
        }
    }

    @Override
    public Song getSongById(UUID id) {
        List<String> tag = new ArrayList<>();
        ResultSet songSet;
        ResultSet tagSet;
        Song song = null;
        try {
            selectSong.setString(1, id.toString());
            songSet = selectSong.executeQuery();
            while (songSet.next()) {
                selectTag.setString(1, songSet.getString(1));
                tagSet = selectTag.executeQuery();
                tag.clear();
                while (tagSet.next()) {
                    tag.add(tagSet.getString(1));
                }
                song = new Song(UUID.fromString(songSet.getString(1)), songSet.getString(2), songSet.getLong(3), tag.toArray(new String[0]));
            }
            return song;
        } catch (SQLException throwable) {
            throw new DBException(throwable);
        }
    }

    @Override
    public Map<UUID, Song> getAllSongs() {
        Map<UUID, Song> map = new HashMap<>();
        List<String> tag = new ArrayList<>();
        ResultSet songSet;
        ResultSet tagSet;
        try {
            songSet = selectAllSong.executeQuery();
            while (songSet.next()) {
                selectTag.setString(1, songSet.getString(1));
                tagSet = selectTag.executeQuery();
                tag.clear();
                while (tagSet.next()) {
                    tag.add(tagSet.getString(1));
                }
                map.put(UUID.fromString(songSet.getString(1)), new Song(UUID.fromString(songSet.getString(1)), songSet.getString(2), songSet.getLong(3), tag.toArray(new String[0])));
            }

        } catch (SQLException throwable) {
            throw new DBException(throwable);
        }
        return map;
    }

    @Override
    public void addSongs(Song... songs) throws SQLException {
        List<String> tag;
        try {
            for (Song song : songs) {
                insertSong.setString(1, song.getId().toString());
                insertSong.setString(2, song.getName());
                insertSong.setLong(3, song.getLength());
                insertSong.execute();
                insertTag.setString(1, song.getId().toString());
                tag = song.getTags();
                for (String t : tag) {
                    insertTag.setString(2, t);
                    insertTag.execute();
                }
            }
            con.commit();
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }

    }

    @Override
    public void deleteSongById(UUID id) throws SQLException {
        try {
            deleteSongTag.setString(1, id.toString());
            deleteSongTag.execute();
            deleteSongById.setString(1, id.toString());
            deleteSongById.setString(1, id.toString());
            deleteSongById.execute();
            con.commit();
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }
    }

    @Override
    public void renameSongById(UUID id, String newName) throws SQLException {
        try {
            renameSong.setString(1, newName);
            renameSong.setString(2, id.toString());
            renameSong.execute();
            con.commit();
        } catch (SQLException throwable) {
            con.rollback();
            throw new DBException(throwable);
        }
    }
}
