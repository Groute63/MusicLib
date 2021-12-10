package com.company.storage.dao;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ArtistDao {
    void saveArtist(String fileName, Artist... artists) throws FileNotFoundException;

    void loadArtist(String fileName) throws IOException;

    Artist getArtistById(int id);

    void addArtist(Artist... artists);

    Artist[] getAllArtist();

    void deleteArtistById(int id);

    void renameArtistById(int id, String newName);

    void addNewSongsCollections(int ArtistId, Album... albums);

    void deleteSongsCollectionsById(int artistId, int albumId);
}
