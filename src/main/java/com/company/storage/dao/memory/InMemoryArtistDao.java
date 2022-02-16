package com.company.storage.dao.memory;

import com.company.entityclass.Album;
import com.company.entityclass.Artist;
import com.company.storage.dao.ArtistDao;

import java.util.HashMap;
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
            artistMap.put(artist.getId(), artist);
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
    public void addNewAlbum(UUID artistId, Album... albums) {
        artistMap.get(artistId).addAlbums(albums);
    }
}
