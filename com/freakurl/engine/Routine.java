package com.freakurl.engine;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Container-Klasse für Game-Assets.
 * 
 * @author Jakob
 */
public class Routine {
    /**
     * Eine Zusammenfassung des Games.
     * 
     * <p>
     * Diese sollte kurz und direkt sein. Eine maximale Länge wird jedoch nicht durchgesetzt.
     * </p>
     */
    public final String summary;
    
    /**
     * Optionale globale Hintergrundmusik.
     * 
     * <p>
     * Sie wird in Dauerschleife wiedergegeben.
     * <br>Die Datei hat einen absoluten Dateipfad. Dieser kann mit beispielsweise {@link File#getPath()} ausgelesen werden.
     * </p>
     */
    public final Optional<File> sound;
    
    /**
     * Alle verfügbaren Frames.
     * 
     * <p>
     * Kann nicht mehr bearbeitet werden, da es sich um eine Unmodifiable List handelt. Siehe {@link List#of}
     * <br>Sollte niemals leer sein, da sonst beim Parsen ein Fehler geschmissen wird.
     * </p>
     */
    public final List<Frame> frames;
    
    Routine(
        String summary,
        String parent,
        String sound,
        Frame[] frames
    ) {
        this.summary = summary;
        
        if (sound != null) {
            File soundFile = new File(parent, sound);
            this.sound = Optional.ofNullable(soundFile.exists() ? soundFile : null);
        } else {
            this.sound = Optional.empty();
        }
        
        this.frames = List.of(frames);
    }
}
