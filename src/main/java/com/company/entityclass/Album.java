package com.company.entityclass;

import java.util.UUID;

public class Album extends SongCollection{

    private final AlbumType enumAlbum = AlbumType.ALBUM;

    public Album(UUID id ,String name) {
        super(id,name);
    }

    public Album(UUID id ,String name, Song... songs) {
        super(id,name, songs);
    }

    public Album(String name, Song... songs) {
        super(name, songs);
    }

    @Override
    public AlbumType getType() {
        return enumAlbum;
    }

}
