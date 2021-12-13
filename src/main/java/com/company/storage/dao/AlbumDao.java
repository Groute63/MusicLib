package com.company.storage.dao;

import com.company.entityClass.Album;
import com.company.entityClass.Song;

import java.util.Map;
import java.util.UUID;

public interface AlbumDao {

    Album getAlbumById(UUID id);

    void addAlbum(Album... albums);

    Map<UUID, Album> getAllAlbum();

    void deleteAlbumById(UUID id);

    void renameAlbumById(UUID id, String newName);

    void addNewSongs(UUID albumId, Song... songs);

    void deleteSongById(UUID albumId, int songPos);
}
