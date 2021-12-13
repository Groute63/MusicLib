package com.company.ui;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.entityClass.Song;
import com.company.storage.DaoType;
import com.company.storage.InMemoryStorage;
import com.company.storage.Storage;

import java.io.IOException;
import java.util.*;

public class View {
    public void start() {
        boolean start = true;
        final Storage controller = new InMemoryStorage();
        final Scanner console = new Scanner(System.in);
        while (start) {
            try {
                System.out.println("С чем вы хотите работать");
                System.out.println("1 - Артисты");
                System.out.println("2 - Альбомы");
                System.out.println("3 - Треки");
                switch (console.nextLine()) {
                    case ("1"): {
                        artistMenu(controller, console);
                        break;
                    }
                    case ("2"): {
                        albumMenu(controller, console);
                        break;
                    }
                    case ("3"): {
                        songMenu(controller, console);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void artistMenu(Storage controller, Scanner console) throws IOException {
        while (true) {
            System.out.println("1 - Создать исполнителя");
            System.out.println("2 - Распечатать всех артистов");
            System.out.println("3 - Удалить исполнителя");
            System.out.println("4 - Добавить альбом из уже созданных");
            System.out.println("5 - Сохранить");
            System.out.println("6 - Загрузить");
            System.out.println("7 - Назад");
            switch (console.nextLine()) {
                case ("1"): {
                    controller.add(createArtist(console), DaoType.ARTIST);
                    break;
                }
                case ("2"): {
                    controller.printAll(DaoType.ARTIST);
                    break;
                }
                case ("3"): {
                    controller.delete(getDeleteNumber(controller, console, DaoType.ARTIST), DaoType.ARTIST);
                    break;
                }
                case ("4"): {
                    controller.addIn(getAddNumber(controller, console, DaoType.ARTIST), getAddEl(controller, console, DaoType.ALBUM), DaoType.ARTIST);
                    break;
                }
                case ("5"): {
                    controller.save(getFileName(console),DaoType.ARTIST);
                    break;
                }
                case ("6"): {
                    controller.load(getFileName(console),DaoType.ARTIST);
                    break;
                }
                case ("7"): {
                    return;
                }
            }
        }
    }

    private void albumMenu(Storage controller, Scanner console) throws IOException {
        while (true) {
            System.out.println("1 - Создать Альбом");
            System.out.println("2 - Распечатать все альбомы");
            System.out.println("3 - Удалить альбом");
            System.out.println("4 - Добавить песню из уже созданных");
            System.out.println("5 - Сохранить");
            System.out.println("6 - Загрузить");
            System.out.println("7 - Назад");
            switch (console.nextLine()) {
                case ("1"): {
                    controller.add(createAlbum(console), DaoType.ALBUM);
                    break;
                }
                case ("2"): {
                    controller.printAll(DaoType.ALBUM);
                    break;
                }
                case ("3"): {
                    controller.delete(getDeleteNumber(controller, console, DaoType.ALBUM), DaoType.ALBUM);
                    break;
                }
                case ("4"): {
                    controller.addIn(getAddNumber(controller, console, DaoType.ALBUM), getAddEl(controller, console, DaoType.SONG), DaoType.ALBUM);
                    break;
                }
                case ("5"): {
                    controller.save(getFileName(console),DaoType.ALBUM);
                    break;
                }
                case ("6"): {
                    controller.load(getFileName(console),DaoType.ALBUM);
                    break;
                }
                case ("7"): {
                    return;
                }
            }
        }
    }

    private void songMenu(Storage controller, Scanner console) throws IOException {
        while (true) {
            System.out.println("1 - Создать песню");
            System.out.println("2 - Распечатать все песни");
            System.out.println("3 - Удалить песню");
            System.out.println("4 - Сохранить");
            System.out.println("5 - Загрузить");
            System.out.println("6 - Назад");
            switch (console.nextLine()) {
                case ("1"): {
                    controller.add(createSong(console), DaoType.SONG);
                    break;
                }
                case ("2"): {
                    controller.printAll(DaoType.SONG);
                    break;
                }
                case ("3"): {
                    controller.delete(getDeleteNumber(controller, console, DaoType.SONG), DaoType.SONG);
                    break;
                }
                case ("4"): {
                     controller.save(getFileName(console),DaoType.SONG);
                    break;
                }
                case ("5"): {
                    controller.load(getFileName(console),DaoType.SONG);
                    break;
                }
                case ("6"): {
                    return;
                }
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

    private int getDeleteNumber(Storage controller, Scanner console, DaoType type) {
        controller.printName(type);
        System.out.println("Введите номер, который хотите удалить");
        return Integer.parseInt(console.nextLine());
    }

    private int getAddNumber(Storage controller, Scanner console, DaoType type) {
        controller.printName(type);
        System.out.println("Введите номер, к которому хотите добавить");
        return Integer.parseInt(console.nextLine());
    }

    private int getAddEl(Storage controller, Scanner console, DaoType type) {
        controller.printName(type);
        System.out.println("Введите номер, который хотите добавить");
        return Integer.parseInt(console.nextLine());
    }

    private String getFileName(Scanner console) {
        System.out.println("Введите название файла");
        return console.nextLine();
    }
}