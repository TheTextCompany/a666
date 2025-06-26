package com.freakurl.a666;

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.EngineException;
import java.io.File;
import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.event.*;

/**
 * Klasse zum Abspielen von WAV-Dateien.
 * 
 * @author Julian Hack.
 */
public class SoundRenderer {
    private Clip clip;
    private File sound;
    public SoundRenderer(File sound){
        this.sound = sound;
    }

    native int play_mp3(char[] file_path);
    /**
     * Lädt die DLL, konvertiert den MP3-Dateipfad und übergibt diesen der play_mp3-Funktion.
     * 
     * @param fileMp3 Der Dateipfad der abzuspielenden MP3-Datei.
     */
    public void renderMp3() {
        System.loadLibrary("sound_renderer_win.dll");
        String filePathMp3 = sound.getAbsolutePath();
        char[] filePathArr = new char[filePathMp3.length() + 1];
        filePathArr = filePathMp3.toCharArray();
        filePathArr[filePathMp3.length() + 1] = '\0';
        play_mp3(filePathArr);
    }

    /**
     * Stoppt ggf. das Abspielen einer WAV-Datei und spielt die eingegebene WAV-Datei ab.
     * 
     * @param fileWav ein File-Objekt einer WAV-Datei.
     * @throws EngineException Wird geschmissen, wenn die WAV-Datei nicht abgespielt werden konnte.
     */
    public void renderWav() throws EngineException {
        try {
            if(clip != null && clip.isRunning()) {
                clip.stop();
            }

            AudioInputStream stream = AudioSystem.getAudioInputStream(sound);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.setFramePosition(0);
            clip.start();
        }

        catch (Exception e) {
            throw new EngineException("Audio-Datei konnte nicht abgespielt werden");
        }
    }
}
