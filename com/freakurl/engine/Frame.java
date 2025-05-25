package com.freakurl.engine;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Container-Klasse für eine Textbox.
 * 
 * @author Jakob
 */
public class Frame {
    /**
     * Eine einzigartige ID, welche diesen Frame identifiziert.
     * 
     * <p>
     * Diese kann verwendet werden, um diesen Frame zu laden in {@link FreakEngine#getFrame(id)}.
     * </p>
     */
    public final int id;
    
    /**
     * Der Autor dieses Frames.
     * 
     * <p>
     * Hier wird eigentlich nur ein Feld unterstützt, jedoch können mehrere Autoren durch ein Komma getrennt angegeben werden.
     * </p>
     */
    public final Optional<String> author;
    
    /**
     * Als Zusammenfassung dienender Titel.
     * 
     * <p>
     * Dieser wird vielleicht, vielleicht aber auch nicht verwendet.
     * <br>Er sollte nicht zu lang sein.
     * </p>
     */
    public final String title;
    
    /**
     * Der Hauptinhalt.
     * 
     * <p>
     * Der Text dieses Frames, welcher dem Nutzer präsentiert werden kann.
     * <br>Dieser hat eine unbestimmte Länge.
     * </p>
     */
    public final String text;
    
    /**
     * Optional angezeigtes Bild.
     * 
     * <p>
     * Das Bild, welches angezeigt werden soll.
     * <br>Sollte dieses nicht vorhanden sein, wird das vorherige Bild beibehalten.
     * <br>Die Datei hat einen absoluten Dateipfad. Dieser kann mit beispielsweise {@link File#getPath()} ausgelesen werden.
     * </p>
     */
    public final Optional<File> image;
    
    /**
     * Optional abzuspielender Soundeffekt.
     * 
     * <p>
     * Dieser wird einmalig beim Anzeigen des Frames ausgegeben.
     * <br>Die Datei hat einen absoluten Dateipfad. Dieser kann mit beispielsweise {@link File#getPath()} ausgelesen werden.
     * </p>
     */
    public final Optional<File> sound;
    
    /**
     * Verfügbare Optionen.
     * 
     * <p>
     * Der Nutzer bekommt eine Auswahl zwischen diesen präsentiert.
     * <br>Es kann daraufhin entscheiden, mit welcher Option er weitergehen möchte. Siehe {@link FrameOption#to}.
     * </p>
     */
    public final List<FrameOption> options;
    
    Frame(
        int id,
        String author,
        String title,
        String text,
        String parent,
        String image,
        String sound,
        FrameOption[] options
    ) {
        this.id = id;
        this.author = Optional.ofNullable(author);
        
        this.title = title;
        this.text = text;
        
        if (image != null) {
            File imageFile = new File(parent, image);
            this.image = Optional.ofNullable(imageFile.exists() ? imageFile : null);
        } else {
            this.image = Optional.empty();
        }
        
        if (sound != null) {
            File soundFile = new File(parent, sound);
            this.sound = Optional.ofNullable(soundFile.exists() ? soundFile : null);
        } else {
            this.sound = Optional.empty();
        }
        
        this.options = List.of(options);
    }
}
