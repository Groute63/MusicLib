package com.company.entityClass;

import java.io.Serializable;

public class Playlist extends SongCollection implements Serializable {
    private final AlbumType enumAlbum = AlbumType.PLAYLIST;

    public Playlist(String name, Song... songs) {
        super(name, songs);
    }

    @Override
    public AlbumType getType() {
        return enumAlbum;
    }
}


