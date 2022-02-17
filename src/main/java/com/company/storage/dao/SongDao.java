package com.company.storage.dao;

import com.company.entityclass.Song;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface SongDao {

    Song getSongById(UUID id);

    Map<UUID,Song> getAllSongs();

    void addSongs(Song ... song) throws SQLException;

    void deleteSongById(UUID id) throws SQLException;

    void renameSongById(UUID id, String newName) throws SQLException;

}
