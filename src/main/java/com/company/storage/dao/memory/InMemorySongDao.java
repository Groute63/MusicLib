package com.company.storage.dao.memory;

import com.company.entityClass.Song;
import com.company.storage.dao.SongDao;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;

public class InMemorySongDao implements SongDao {
    private final List<Song> songList = new ArrayList();
    private int currentId = 0;
    @Override
    public void saveSongs(String fileName, Song... song) throws FileNotFoundException {
        Gson gson = new Gson();
        PrintWriter printWriter = new PrintWriter(fileName);
        for (int i = 0; i < song.length; i++) {
            String json = gson.toJson(song[i]);
            printWriter.println(json);
        }
        printWriter.flush();
    }

    @Override
    public void loadSongs(String fileName) throws IOException {
        Gson gson = new Gson();
        String[] arr = Files.readAllLines(Paths.get(fileName)).toArray(new String[0]);
        for (int i = 0; i < arr.length; i++) {
            songList.add(gson.fromJson(arr[i], Song.class));
        }
    }

    @Override
    public Song getSongById(int id) {
        for (int i = 0; i<songList.size();i++)
            if(songList.get(i).getId() == id)
                return songList.get(i);
        return null;
    }

    @Override
    public Song[] getAllSongs() {
        return songList.toArray(new Song[0]);
    }

    @Override
    public void addSongs(Song... song) {
        for(int i = 0; i < song.length;i++)
        {
            song[i].setId(currentId);
            currentId++;
            songList.add(song[i]);
        }
    }

    @Override
    public void deleteSongById(int id) {
        for (int i = 0; i<songList.size();i++)
            if(songList.get(i).getId() == id) {
                songList.remove(i);
                break;
            }
    }

    @Override
    public void renameSongById(int id, String newName) {
        for (int i = 0; i<songList.size();i++)
            if(songList.get(i).getId() == id) {
                songList.get(i).setName(newName);
                break;
            }
    }

}
