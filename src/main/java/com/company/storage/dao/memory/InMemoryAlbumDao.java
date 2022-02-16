package com.company.storage.dao.memory;

import com.company.entityclass.Album;
import com.company.entityclass.Song;

import com.company.storage.dao.AlbumDao;

import java.util.*;

public class InMemoryAlbumDao implements AlbumDao {

    private final Map<UUID, Album> albumMap = new HashMap<>();

    @Override
    public Album getAlbumById(UUID id) {
        return albumMap.get(id);
    }

    @Override
    public void addAlbum(Album... albums) {
        for (Album album : albums) {
            albumMap.put(album.getId(), album);
        }
    }

    @Override
    public Map<UUID, Album> getAllAlbum() {
        return albumMap;
    }

    @Override
    public void deleteAlbumById(UUID id) {
        albumMap.remove(id);
    }

    @Override
    public void renameAlbumById(UUID id, String newName) {
        albumMap.get(id).setName(newName);
    }

    @Override
    public void addNewSongs(UUID albumId, Song... songs) {
        albumMap.get(albumId).addSongs(songs);
    }
}
