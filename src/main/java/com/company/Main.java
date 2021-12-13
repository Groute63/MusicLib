package com.company;

import com.company.entityClass.Album;
import com.company.entityClass.Artist;
import com.company.entityClass.Song;
import com.company.storage.dao.memory.InMemoryAlbumDao;
import com.company.storage.dao.memory.InMemoryArtistDao;
import com.company.ui.View;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        new View().start();
       /* Song s = new Song("asd",1,"asd");
        Album a1= new Album("1");
        Album a2= new Album("2",s);
        Album a3= new Album("3");
        Artist r = new Artist("lexa", a1,a2,a3);
        InMemoryAlbumDao  a= new InMemoryAlbumDao();
        InMemoryArtistDao b = new InMemoryArtistDao();
        a.addAlbum(a1,a2,a3);
      //  a.getAlbumById((UUID) a.getAllAlbum().keySet().toArray()[1]).setName("123");

      //  System.out.println(a.getAlbumById((UUID) a.getAllAlbum().keySet().toArray()[1]).getName());
      //  System.out.println(a.getAlbumById((UUID) a.getAllAlbum().keySet().toArray()[1]).getSongs());

        b.addArtist(r);
        b.addArtist(r);

        b.deleteAlbumById((UUID) b.getAllArtist().keySet().toArray()[0],0);
       System.out.println( b.getArtistById((UUID) b.getAllArtist().keySet().toArray()[0]).getAlbums().get(0));*/
    }
}
