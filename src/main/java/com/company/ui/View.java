package com.company.ui;

import com.company.entityclass.Album;
import com.company.entityclass.Artist;
import com.company.entityclass.Song;
import com.company.entityclass.role.AdminRole;
import com.company.entityclass.role.ArtistRole;
import com.company.entityclass.role.Role;
import com.company.entityclass.role.UserRole;
import com.company.storage.DBStorage;
import com.company.storage.DaoType;
import com.company.storage.Storage;

import java.util.*;

public class View {
    public void start() {
        boolean start = true;
        int access = 0;
        String login;
        String pas;
        Role role = null;
        final Scanner console = new Scanner(System.in);
        try (final DBStorage controller = new DBStorage()) {
            while (access == 0) {
                System.out.println("Введите логин");
                login = console.nextLine();
                System.out.println("Введите пароль");
                pas = console.nextLine();
                access = controller.login(login,pas);
                if(access == 0) System.out.println("Неверный логин/пароль");
            }
            switch (access){
                case (1):{
                    role = new AdminRole();
                    break;
                }
                case (2):{
                    role = new ArtistRole();
                    break;
                }
                case (3):{
                    role = new UserRole();
                    break;
                }
            }
            while (start) {
                System.out.println("С чем вы хотите работать");
                System.out.println("1 - Артисты");
                System.out.println("2 - Альбомы");
                System.out.println("3 - Треки");
                System.out.println("4 - Выход");
                switch (console.nextLine()) {
                    case ("1"): {
                        artistMenu(controller, console,role);
                        break;
                    }
                    case ("2"): {
                        albumMenu(controller, console,role);
                        break;
                    }
                    case ("3"): {
                        songMenu(controller, console,role);
                        break;
                    }
                    case ("4"): {
                        start = false;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void artistMenu(Storage controller, Scanner console,Role role){
        while (true) {
            System.out.println("1 - Создать исполнителя");
            System.out.println("2 - Распечатать всех артистов");
            System.out.println("3 - Удалить исполнителя");
            System.out.println("4 - Добавить альбом из уже созданных");
            System.out.println("5 - Назад");
            switch (console.nextLine()) {
                case ("1"): {
                    if(role.getAccess() <3)
                    controller.add(createArtist(console), DaoType.ARTIST);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("2"): {
                    controller.printAll(DaoType.ARTIST);
                    break;
                }
                case ("3"): {
                    if(role.getAccess() <2)
                    controller.delete(getDeleteNumber(controller, console, DaoType.ARTIST), DaoType.ARTIST);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("4"): {
                    if(role.getAccess()<2)
                    controller.addIn(getAddNumber(controller, console, DaoType.ARTIST), getAddEl(controller, console, DaoType.ALBUM), DaoType.ARTIST);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("5"): {
                    return;
                }
            }
        }
    }

    private void albumMenu(Storage controller, Scanner console,Role role){
        while (true) {
            System.out.println("1 - Создать Альбом");
            System.out.println("2 - Распечатать все альбомы");
            System.out.println("3 - Удалить альбом");
            System.out.println("4 - Добавить песню из уже созданных");
            System.out.println("5 - Назад");
            switch (console.nextLine()) {
                case ("1"): {
                    if(role.getAccess() <3)
                    controller.add(createAlbum(console), DaoType.ALBUM);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("2"): {
                    controller.printAll(DaoType.ALBUM);
                    break;
                }
                case ("3"): {
                    if(role.getAccess() <2)
                    controller.delete(getDeleteNumber(controller, console, DaoType.ALBUM), DaoType.ALBUM);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("4"): {
                    if(role.getAccess()<2)
                    controller.addIn(getAddNumber(controller, console, DaoType.ALBUM), getAddEl(controller, console, DaoType.SONG), DaoType.ALBUM);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("5"): {
                    return;
                }
            }
        }
    }

    private void songMenu(Storage controller, Scanner console,Role role){
        while (true) {
            System.out.println("1 - Создать песню");
            System.out.println("2 - Распечатать все песни");
            System.out.println("3 - Удалить песню");
            System.out.println("4 - Назад");
            switch (console.nextLine()) {
                case ("1"): {
                    if(role.getAccess()<3)
                    controller.add(createSong(console), DaoType.SONG);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("2"): {
                    controller.printAll(DaoType.SONG);
                    break;
                }
                case ("3"): {
                    if(role.getAccess()<2)
                    controller.delete(getDeleteNumber(controller, console, DaoType.SONG), DaoType.SONG);
                    else System.out.println("Нарушение прав доступа");
                    break;
                }
                case ("4"): {
                    return;
                }
            }
        }
    }

    private void register(Scanner console){
        String login;
        String password;
        System.out.println("Введите логин");
        login = console.nextLine();
        System.out.println("Введите пароль");
        System.out.println("Выбирите вашу роль");
        System.out.println("1-Админ");
        System.out.println("2-Артист");
        System.out.println("3-Пользователь");
    }

    private Artist createArtist(Scanner console) {
        System.out.println("Введите имя артиста");
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

    private UUID getDeleteNumber(Storage controller, Scanner console, DaoType type) {
        controller.printName(type);
        System.out.println("Введите номер, который хотите удалить");
        return getUuid(controller, Integer.parseInt(console.nextLine()), type);
    }

    private UUID getAddNumber(Storage controller, Scanner console, DaoType type) {
        controller.printName(type);
        System.out.println("Введите номер, к которому хотите добавить");
        return getUuid(controller, Integer.parseInt(console.nextLine()), type);
    }

    private UUID getAddEl(Storage controller, Scanner console, DaoType type) {
        controller.printName(type);
        System.out.println("Введите номер, который хотите добавить");
        return getUuid(controller, Integer.parseInt(console.nextLine()), type);
    }

    private UUID getUuid(Storage controller, int pos, DaoType type) {
        switch (type) {
            case SONG: {
                return controller.get(DaoType.SONG).keySet().toArray(new UUID[0])[pos];
            }
            case ALBUM: {
                return controller.get(DaoType.ALBUM).keySet().toArray(new UUID[0])[pos];
            }
            case ARTIST: {
                return controller.get(DaoType.ARTIST).keySet().toArray(new UUID[0])[pos];
            }
        }
        return null;
    }
}