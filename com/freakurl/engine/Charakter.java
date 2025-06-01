package com.freakurl.engine;

/**
 * Container-Klasse für {@code Character}s.
 * 
 * @author Julian Hack
 * 
 */
public class Charakter
{
    final String id;
    final String name;
    final String imagePath;

    public Charakter(String idNew, String nameNew, String imagePathNew) {
        id = idNew;
        name = nameNew;
        imagePath = imagePathNew;

    }
}
