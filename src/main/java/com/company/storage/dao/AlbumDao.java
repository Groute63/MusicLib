package com.company.storage.dao;

import com.company.entityclass.Album;
import com.company.entityclass.Song;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface AlbumDao {

    Album getAlbumById(UUID id);

    void addAlbum(Album... albums) throws SQLException;

    Map<UUID, Album> getAllAlbum();

    void deleteAlbumById(UUID id) throws SQLException;

    void renameAlbumById(UUID id, String newName) throws SQLException;

    void addNewSongs(UUID albumId, Song... songs) throws SQLException;
}
