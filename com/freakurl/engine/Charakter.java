package com.freakurl.engine;

/**
 * Container-Klasse für {@link Character}s.
 * 
 * @author Julian Hack
 */
public class Charakter {
    final String id;
    final String name;
    final String imagePath;

    public Charakter(String id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;

    }
}
