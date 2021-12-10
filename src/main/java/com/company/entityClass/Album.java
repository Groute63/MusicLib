package com.company.entityClass;

import java.io.Serializable;

public class Album extends SongCollection implements Serializable {

    private final EnumAlbum enumAlbum = EnumAlbum.ALBUM;

    public Album(String name, Song... songs) {
        super(name, songs);
    }

    @Override
    public EnumAlbum getType() {
        return enumAlbum;
    }

}
