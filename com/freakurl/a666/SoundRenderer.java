package com.freakurl.a666;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Helfer-Klasse zum Ausgeben von Tonabläufen.
 * 
 * @author Julian Hack
 */
public class SoundRenderer {
    SoundRenderer(){

    }

    /**
     *  Spielt die jeweilige MP3-Datei ab.
     *  
     *  @param filepath Dateipfad zur MP3-Datei
     */
    void playMp3 (String filepath) {

        MediaPlayer player;
        Media audioFile = new Media(new File(filepath).toURI().toString());
        player = new MediaPlayer(audioFile);
        player.play();
    }
}

