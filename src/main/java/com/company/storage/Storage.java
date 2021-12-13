package com.company.storage;

import com.company.entityClass.Artist;
import com.company.entityClass.EntityClassMarker;

import java.io.IOException;

public interface Storage {
    void add(EntityClassMarker obj, DaoType type);

    void printAll(DaoType type);

    void printName(DaoType type);

    void delete(int pos,DaoType type);

    void addIn(int pos, int posAlbum, DaoType type);

    void load(String file,DaoType type) throws IOException;

    void save(String file,DaoType type) throws IOException;
}
