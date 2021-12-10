package com.company.storage.dao.memory;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.storage.dao.ArtistDao;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InMemoryArtistDao implements ArtistDao {
    private final List<Artist> artistList = new ArrayList();
    private int currentId = 0;

    @Override
    public void saveArtist(String fileName, Artist... artists) throws FileNotFoundException {
        Gson gson = new Gson();
        PrintWriter printWriter = new PrintWriter(fileName);
        for (int i = 0; i < artists.length; i++) {
            String json = gson.toJson(artists[i]);
            printWriter.println(json);
        }
        printWriter.flush();
    }

    @Override
    public void loadArtist(String fileName) throws IOException {
        Gson gson = new Gson();
        String[] arr = Files.readAllLines(Paths.get(fileName)).toArray(new String[0]);
        for (int i = 0; i < arr.length; i++) {
            artistList.add(gson.fromJson(arr[i], Artist.class));
        }
    }

    @Override
    public Artist getArtistById(int id) {
        for (int i = 0; i < artistList.size(); i++)
            if (artistList.get(i).getId() == id)
                return artistList.get(i);
        return null;
    }

    @Override
    public void addArtist(Artist... artists) {
        for (int i = 0; i < artists.length; i++) {
            artists[i].setId(currentId);
            currentId++;
            artistList.add(artists[i]);
        }
    }

    @Override
    public Artist[] getAllArtist() {
        return artistList.toArray(new Artist[0]);
    }

    @Override
    public void deleteArtistById(int id) {
        for (int i = 0; i < artistList.size(); i++)
            if (artistList.get(i).getId() == id) {
                artistList.remove(i);
                break;
            }
    }

    @Override
    public void renameArtistById(int id, String newName) {
        for (int i = 0; i < artistList.size(); i++)
            if (artistList.get(i).getId() == id) {
                artistList.get(i).setName(newName);
                break;
            }
    }

    @Override
    public void addNewSongsCollections(int ArtistId, Album... albums) {
        for (int i = 0; i < artistList.size(); i++)
            if (artistList.get(i).getId() == ArtistId) {
                artistList.get(i).addAlbums(albums);
                break;
            }
    }

    @Override
    public void deleteSongsCollectionsById(int artistId, int albumId) {
        for (int i = 0; i < artistList.size(); i++)
            if (artistList.get(i).getId() == artistId) {
                artistList.get(i).deleteAlbum(albumId);
            }
    }
}
