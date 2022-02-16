package com.company.storage.dao;

import com.company.entityclass.Album;
import com.company.entityclass.Song;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface AlbumDao {

    Album getAlbumById(UUID id);

    void addAlbum(Album... albums);

    Map<UUID, Album> getAllAlbum();

    void deleteAlbumById(UUID id);

    void renameAlbumById(UUID id, String newName);

    void addNewSongs(UUID albumId, Song... songs);
}
