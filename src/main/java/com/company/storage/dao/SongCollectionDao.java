package com.company.storage.dao;

import com.company.entityClass.Song;
import com.company.entityClass.SongCollection;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SongCollectionDao {
    void saveSongCollections(String fileName, SongCollection... songCollection) throws FileNotFoundException;

    void loadSongCollections(String fileName) throws IOException;

    SongCollection getSongCollectionById(int id);

    void addSongCollection(SongCollection ... songCollections);

    SongCollection[] getAllSongCollection();

    void deleteSongCollectionById(int id);

    void renameSongCollectionById(int id, String newName);

    void addNewSongs(int albumId,Song ... songs);

    void deleteSongById(int songCollectionId, int songId);
}
