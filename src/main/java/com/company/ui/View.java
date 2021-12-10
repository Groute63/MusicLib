package com.company.ui;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.entityClass.Song;
import com.company.storage.InMemoryStorage;
import com.company.storage.Storage;

import java.util.*;

public class View {
    public void start() {
        boolean start = true;
        final Storage controller = new InMemoryStorage();
        final Scanner console = new Scanner(System.in);
        while (start) {
            try {
                System.out.println("1 - Создать исполнителя");
                System.out.println("2 - Распечатать всех артистов");
                System.out.println("3 - Удалить исполнителя");
                System.out.println("4 - Сохранить");
                System.out.println("5 - Загрузить");
                switch (console.nextLine()) {
                    case ("1"): {
                        controller.addArtist(createArtist(console));
                        break;
                    }
                    case ("2"): {
                        controller.printArtist();
                        break;
                    }
                    case ("3"): {
                        controller.deleteArtist(deleteSong(controller,console));
                        break;
                    }
                    case ("4"): {
                        controller.save(getFileName(console));
                        break;
                    }
                    case ("5"): {
                        controller.load(getFileName(console));
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Artist createArtist(Scanner console) {
        System.out.println("Введите имя артиста  ");
        String artistName = console.nextLine();
        System.out.println("Введите количество альбомов  ");
        int albumCount = Integer.parseInt(console.nextLine());
        Set<Album> albumSet = new HashSet<>(albumCount);
        for (int i = 0; i < albumCount; i++)
            albumSet.add(createAlbum(console));
        return new Artist(artistName, albumSet.toArray(new Album[0]));
    }

    private Album createAlbum(Scanner console) {
        System.out.println("Введите название альбома ");
        String albumName = console.nextLine();
        System.out.println("Введите количество треков  ");
        int songCount = Integer.parseInt(console.nextLine());
        Set<Song> songSet = new HashSet<>(songCount);
        for (int i = 0; i < songCount; i++)
            songSet.add(createSong(console));
        return new Album(albumName, songSet.toArray(new Song[0]));
    }

    private Song createSong(Scanner console) {
        System.out.println("Введите название трека");
        String songName = console.nextLine();
        System.out.println("Введите его длительность");
        long songLength = Long.parseLong(console.nextLine());
        System.out.println("Введите его теги через пробел");
        String[] tags = console.nextLine().split(" ");
        return new Song(songName, songLength, tags);
    }

    private int deleteSong(Storage controller,Scanner console) {
        controller.printArtistName();
        System.out.println("Введите номер исполнителя которого хотите удалить");
        return Integer.parseInt(console.nextLine());
    }

    private String getFileName(Scanner console) {
        System.out.println("Введите название файла");
        return console.nextLine();
    }
}