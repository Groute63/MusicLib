package com.company.entityclass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Song implements Serializable,EntityClassMarker {

    private String name;
    private long lengthMin;
    private List<String> tags;
    private UUID id;

    public Song(String name, long length, String... tags) {
        this.name = name;
        this.lengthMin = length;
        this.tags = new ArrayList<>(tags.length);
        Collections.addAll(this.tags, tags);
        id = UUID.randomUUID();
    }

    public Song(UUID id, String name, long length, String... tags) {
        this.id = id;
        this.name = name;
        this.lengthMin = length;
        this.tags = new ArrayList<>(tags.length);
        Collections.addAll(this.tags, tags);
    }

    public void setId(UUID newId){
        id=newId;
    }

    public UUID getId(){
        return id;
    }

    public String toString() {
        return "Song{NAME - " + name + " / LENGTH - " + lengthMin + " / TAGS - " + tags + "}";
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return lengthMin;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(long length) {
        this.lengthMin = length;
    }

    public void setTags(String... tags) {
        this.tags = new ArrayList<>(tags.length);
        Collections.addAll(this.tags, tags);
    }
}
