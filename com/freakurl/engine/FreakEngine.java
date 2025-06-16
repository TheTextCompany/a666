package com.freakurl.engine;

import java.util.ArrayList;

/**
 * Haupt-Engine des Projekts.
 * 
 * @author Jakob
 */
public class FreakEngine {    
    final Routine r;
    final ArrayList<String> flags = new ArrayList<>();
    String nextFlag;
    
    public FreakEngine() throws EngineException {
        String assets;
        try {
            assets = FreakEngine.class
              .getProtectionDomain()
              .getCodeSource()
              .getLocation()
              .toURI()
              .getPath();
        } catch (Exception e) {
            throw new EngineException("Unable to get asset directory: " + e.getMessage());
        }
        
        try {
            r = Interpreter.loadRoutine(assets);
        } catch (Exception e) {
            throw new EngineException("Failed to initialize engine: " + e.getMessage());
        }
    }
    
    /**
     * Lade einen {@link Frame} mit einer {@code ID}.
     * 
     * @param id Die zu ladende ID.
     * @return Der angeforderte Frame.
     * @throws EngineException Wird geschmissen, sollte der {@link Frame} nicht existieren.
     */
    public Frame getFrame(int id) throws EngineException {
        for (var i : r.frames) {
            if (id == i.id) {
                if (nextFlag != null) {
                    flags.add(nextFlag);
                    nextFlag = null;
                }
                if (i.flag.isPresent() && !flags.contains(i.flag.get())) {
                    nextFlag = i.flag.get();
                }
                return i.copyWithFlags(flags);
            }
        }
        throw new EngineException("No frame registered of ID: " + id);
    }
    
    /**
     * Crasht die Engine durch einen {@link StackOverflowError}.
     * 
     * <p>
     * Besser nicht aufrufen, wenn nicht gewollt.
     * <br>Ist eigentlich nur als Witz gedacht.
     * </p>
     */
    public void crash() {
        crash();
    }
}
