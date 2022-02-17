package com.company.storage.dao;

import com.company.entityclass.Album;
import com.company.entityclass.Artist;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface ArtistDao {

    Artist getArtistById(UUID id);

    void addArtist(Artist... artists) throws SQLException;

    Map<UUID, Artist> getAllArtist();

    void deleteArtistById(UUID id) throws SQLException;

    void renameArtistById(UUID id, String newName) throws SQLException;

    void addNewAlbum(UUID ArtistId, Album... albums) throws SQLException;
}
