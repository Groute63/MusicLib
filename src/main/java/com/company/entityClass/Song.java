package com.company.entityClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Song implements Serializable {

    private String name;
    private long lengthMin;
    private List<String> tags;
    private int id;

    public Song(String name, long length, String... tags) {
        this.name = name;
        this.lengthMin = length;
        this.tags = new ArrayList<>(tags.length);
        Collections.addAll(this.tags, tags);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Song{NAME - " + name + " / LENGTH - " + lengthMin + " / TAGS - " + tags + "}";
    }

    public String getName() {
        return name;
    }

    public double getLength() {
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
