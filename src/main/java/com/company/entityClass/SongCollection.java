package com.company.entityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SongCollection implements EntityClassMarker {
    private String name;
    private List<Song> songs;

    public abstract AlbumType getType();

    public SongCollection(String name, Song... songs) {
        this.name = name;
        this.songs = new ArrayList<>(songs.length);
        Collections.addAll(this.songs, songs);
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append("Album{NAME - ").append(name).append('\n');
        for (int i = 0; i < songs.size(); i++)
            b.append("               ").append(songs.get(i)).append('\n');
        b.append("                      }");
        return b.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(Song... songs) {
        this.songs = new ArrayList<>();
        Collections.addAll(this.songs, songs);
    }

    public double getLength() {
        double length = 0;
        for (Song song : songs) {
            length += song.getLength();
        }
        return length;
    }

    public List<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        for (Song song : songs) {
            tags.addAll(song.getTags());
        }
        return tags;
    }

    public void addSongs(Song... song) {
        Collections.addAll(songs, song);
    }
}
