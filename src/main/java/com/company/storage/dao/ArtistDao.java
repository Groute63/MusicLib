package com.company.storage.dao;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.entityClass.Song;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ArtistDao {

    Artist getArtistById(UUID id);

    void addArtist(Artist... artists);

    Map<UUID, Artist> getAllArtist();

    void deleteArtistById(UUID id);

    void renameArtistById(UUID id, String newName);

    void addNewSongsCollections(UUID ArtistId, Album... albums);

    void deleteAlbumById(UUID artistId, int albumPos);

    Album  getAlbumById(UUID artistId, int albumPos);

    List<Song> getAllSongInAlbumById(UUID artistId, int albumPos);
}
