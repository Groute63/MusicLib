package com.company.entityClass;

public class Album extends SongCollection{

    private final AlbumType enumAlbum = AlbumType.ALBUM;

    public Album(String name, Song... songs) {
        super(name, songs);
    }

    @Override
    public AlbumType getType() {
        return enumAlbum;
    }

}
