package com.freakurl.a666;

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.EngineException;
/**
 * Klasse zum Abspielen von WAV-Dateien.
 * 
 * @author Julian Hack.
 */
public class SoundRenderer {

    public static native boolean play_wav(String file_path);
    
    /**
     * Spielt die jeweilige WAV-Datei über die DLL ab.
     * 
     * @param filePathWav Der Dateipfad zur WAV-Datei.
     * @throws EngineException Wird geschmissen, wenn die DLL nicht gefunden wurde.
     * @return sound Ob das Abspielen erflogreich war.
     */
    static boolean playWav(String filePathWav) throws EngineException {
        boolean sound = false;
        try {
            System.loadLibrary("sound_renderer.dll");
            sound = play_wav(filePathWav);
         }
         
        catch (UnsatisfiedLinkError e) {
            throw new EngineException("Failed to load DLL ; DLL not found."); 
        }
        
        return sound;
    }
}
