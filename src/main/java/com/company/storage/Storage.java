package com.company.storage;

import com.company.entityclass.EntityClassMarker;

import java.util.Map;
import java.util.UUID;

public interface Storage {
    Map<UUID,?> get(DaoType type);

    void add(EntityClassMarker obj, DaoType type);

    void printAll(DaoType type);

    void printName(DaoType type);

    void delete(UUID id, DaoType type);

    void addIn(UUID id, UUID id2, DaoType type);
}
