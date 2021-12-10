package com.company.entityClass;

import java.io.Serializable;

public class Playlist extends SongCollection implements Serializable {
    private final EnumAlbum enumAlbum = EnumAlbum.PLAYLIST;

    public Playlist(String name, Song... songs) {
        super(name, songs);
    }

    @Override
    public EnumAlbum getType() {
        return enumAlbum;
    }
}


