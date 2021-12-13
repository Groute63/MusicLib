package com.company.storage.dao.memory;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.entityClass.Song;
import com.company.storage.dao.ArtistDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class InMemoryArtistDao implements ArtistDao {
    private final Map<UUID, Artist> artistMap = new HashMap<>();

    @Override
    public Artist getArtistById(UUID id) {
        return artistMap.get(id);
    }

    @Override
    public void addArtist(Artist... artists) {
        for (Artist artist : artists) {
            artistMap.put(UUID.randomUUID(), artist);
        }
    }

    @Override
    public Map<UUID, Artist> getAllArtist() {
        return artistMap;
    }

    @Override
    public void deleteArtistById(UUID id) {
        artistMap.remove(id);
    }

    @Override
    public void renameArtistById(UUID id, String newName) {
        artistMap.get(id).setName(newName);
    }

    @Override
    public void addNewSongsCollections(UUID artistId, Album... albums) {
        artistMap.get(artistId).addAlbums(albums);
    }

    @Override
    public Album getAlbumById(UUID artistId, int albumPos) {
        return artistMap.get(artistId).getAlbums().get(albumPos);
    }

    @Override
    public List<Song> getAllSongInAlbumById(UUID artistId, int albumPos) {
       return artistMap.get(artistId).getAlbums().get(albumPos).getSongs();
    }

    @Override
    public void deleteAlbumById(UUID artistId, int albumPos) {
        artistMap.get(artistId).getAlbums().remove(albumPos);
    }

}
