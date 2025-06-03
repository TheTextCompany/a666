package com.freakurl.engine;

import java.util.Optional;

/**
 * Container-Klasse für {@link Character}s.
 * 
 * @author Julian Hack
 */
public class Character {
    final String id;
    final String name;
    final Optional<Integer> presentedIn;
    final Optional<String> summary;
    
    public Character(String id, String name, Optional<String> summary, Optional<Integer> presentedIn) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.presentedIn = presentedIn;
    }
}
