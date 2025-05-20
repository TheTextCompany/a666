package com.freakurl.engine;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Hauptengine des Projekts.
 * 
 * @author Jakob
 */
public class FreakEngine {
    public final Logger logger = Logger.getLogger("game");
    final Logger loggerInternal = Logger.getLogger(FreakEngine.class.getName());
    
    Interpreter i = new Interpreter();
    Characters c = new Characters();
    Storage s = new Storage();
    
    ArrayList<Event> events = new ArrayList<Event>();
    
    public FreakEngine() {}
}
