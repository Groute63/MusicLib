package com.company.storage.dao;

import com.company.entityClass.Song;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SongDao {
    void saveSongs(String fileName,Song ... song) throws FileNotFoundException;

    void loadSongs(String fileName) throws IOException;

    Song getSongById(int id);

    Song[] getAllSongs();

    void addSongs(Song ... song);

    void deleteSongById(int id);

    void renameSongById(int id, String newName);
}
