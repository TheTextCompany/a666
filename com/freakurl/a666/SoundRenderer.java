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

    public SoundRenderer(){}

    /**
     * Stoppt ggf. das Abspielen einer WAV-Datei und spielt die eingegebene WAV-Datei ab.
     * 
     * @param fileWav ein File-Objekt einer WAV-Datei.
     * @throws EngineException Wird geschmissen, wenn die WAV-Datei nicht abgespielt werden konnte.
     */
    public void playWAV (File fileWav) throws EngineException {
        try {
            if(clip != null && clip.isRunning()) {
                clip.stop();
            }

            AudioInputStream stream = AudioSystem.getAudioInputStream(fileWav);
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
