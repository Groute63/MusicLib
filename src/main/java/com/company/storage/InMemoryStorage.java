package com.company.storage;

import com.company.entityClass.Artist;
import com.company.storage.dao.ArtistDao;
import com.company.storage.dao.SongCollectionDao;
import com.company.storage.dao.SongDao;
import com.company.storage.dao.memory.InMemoryArtistDao;
import com.company.storage.dao.memory.InMemorySongCollectionDao;
import com.company.storage.dao.memory.InMemorySongDao;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InMemoryStorage implements Storage{
    private SongDao songs = new InMemorySongDao();
    private SongCollectionDao songCollections = new InMemorySongCollectionDao();
    private ArtistDao artists = new InMemoryArtistDao();

    public void save(String file) throws IOException {
        Gson gson = new Gson();
        PrintWriter printWriter = new PrintWriter(file);
        for (int i = 0; i < art.size(); i++) {
            String json = gson.toJson(art.get(i));
            System.out.println(json);
            printWriter.println(json);
        }
        printWriter.flush();
    }

    public void load(String file) throws IOException {
        Gson gson = new Gson();
        art.clear();
        String[] arr = Files.readAllLines(Paths.get(file)).toArray(new String[0]);
        for (int i = 0; i < arr.length; i++) {
            art.add(gson.fromJson(arr[i], Artist.class));
            System.out.println(art.get(i));
        }
    }

    public void addArtist(Artist artist) {
        art.add(artist);
    }

    public void printArtist() {
        for (int i = 0; i < art.size(); i++)
            System.out.println(art.get(i));
    }

    public void printArtistName() {
        for (int i = 0; i < art.size(); i++)
            System.out.println(i + " - " + art.get(i).getName());
    }

    public void deleteArtist(int pos) {
        art.remove(pos);
    }
}
