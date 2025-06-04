package com.freakurl.engine;

/**
 * Haupt-Engine des Projekts.
 * 
 * @author Jakob
 */
public class FreakEngine {    
    final Routine r;
    
    Storage s = new Storage();
    
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
                return i;
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
