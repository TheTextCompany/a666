package com.freakurl.engine;

/**
 * Haupt-Engine des Projekts.
 * 
 * @author Jakob
 */
public class FreakEngine {    
    final Routine r;
    
    Characters c = new Characters();
    Storage s = new Storage();
    
    public FreakEngine() throws EngineException {
        try {
            String jarPath = FreakEngine.class
              .getProtectionDomain()
              .getCodeSource()
              .getLocation()
              .toURI()
              .getPath();
            
            r = Interpreter.loadRoutine(jarPath);
        } catch (Exception e) {
            throw new EngineException("Unable to get asset directory: " + e.getMessage());
        }
    }
    
    int crash() {
        return crash();
    }
}
