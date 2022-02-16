package com.company.storage.dao.memory;

import com.company.entityclass.Song;
import com.company.storage.dao.SongDao;

import java.util.*;

public class InMemorySongDao implements SongDao {
    private final Map<UUID, Song> songList = new HashMap<>();

    @Override
    public Song getSongById(UUID id) {
        return songList.get(id);
    }

    @Override
    public Map<UUID, Song> getAllSongs() {
        return songList;
    }

    @Override
    public void addSongs(Song... song) {
        for (Song value : song) {
            songList.put(value.getId(), value);
        }
    }

    @Override
    public void deleteSongById(UUID id) {
        songList.remove(id);
    }

    @Override
    public void renameSongById(UUID id, String newName) {
        songList.get(id).setName(newName);
    }

}
