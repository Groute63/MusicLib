package com.company.storage.dao.memory;

import com.company.entityClass.Song;
import com.company.entityClass.SongCollection;
import com.company.storage.dao.SongCollectionDao;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InMemorySongCollectionDao implements SongCollectionDao {

    private final List<SongCollection> songCollectionList = new ArrayList();
    private int currentId = 0;

    @Override
    public void saveSongCollections(String fileName, SongCollection... songCollection) throws FileNotFoundException {
        Gson gson = new Gson();
        PrintWriter printWriter = new PrintWriter(fileName);
        for (int i = 0; i < songCollection.length; i++) {
            String json = gson.toJson(songCollection[i]);
            printWriter.println(json);
        }
        printWriter.flush();
    }

    @Override
    public void loadSongCollections(String fileName) throws IOException {
        Gson gson = new Gson();
        String[] arr = Files.readAllLines(Paths.get(fileName)).toArray(new String[0]);
        for (int i = 0; i < arr.length; i++) {
            songCollectionList.add(gson.fromJson(arr[i], SongCollection.class));
        }
    }

    @Override
    public SongCollection getSongCollectionById(int id) {
        for (int i = 0; i < songCollectionList.size(); i++)
            if (songCollectionList.get(i).getId() == id)
                return songCollectionList.get(i);
        return null;
    }

    @Override
    public void addSongCollection(SongCollection... songCollections) {
        for (int i = 0; i < songCollections.length; i++) {
            songCollections[i].setId(currentId);
            currentId++;
            songCollectionList.add(songCollections[i]);
        }
    }

    @Override
    public SongCollection[] getAllSongCollection() {
        return songCollectionList.toArray(new SongCollection[0]);
    }

    @Override
    public void deleteSongCollectionById(int id) {
        for (int i = 0; i < songCollectionList.size(); i++)
            if (songCollectionList.get(i).getId() == id) {
                songCollectionList.remove(i);
                break;
            }
    }

    @Override
    public void renameSongCollectionById(int id, String newName) {
        for (int i = 0; i < songCollectionList.size(); i++)
            if (songCollectionList.get(i).getId() == id) {
                songCollectionList.get(i).setName(newName);
                break;
            }
    }

    @Override
    public void addNewSongs(int albumId, Song... songs) {
        for (int i = 0; i < songCollectionList.size(); i++)
            if (songCollectionList.get(i).getId() == albumId) {
                songCollectionList.get(i).addSongs(songs);
                break;
            }
    }

    @Override
    public void deleteSongById(int albumId, int songId) {
        for (int i = 0; i < songCollectionList.size(); i++)
            if (songCollectionList.get(i).getId() == albumId) {
                songCollectionList.get(i).deleteSong(songId);
            }
    }
}
