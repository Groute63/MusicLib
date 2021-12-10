package com.company.storage;

import com.company.entityClass.Artist;

import java.io.IOException;

public interface Storage {
    void addArtist(Artist artist);

    void printArtist();

    void printArtistName();

    void deleteArtist(int pos);
}
